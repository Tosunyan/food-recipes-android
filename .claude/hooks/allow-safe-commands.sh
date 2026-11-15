#!/usr/bin/env bash
# PreToolUse hook shared by Claude Code and GitHub Copilot CLI (both read .claude/settings.json).
# Auto-approves a small allowlist of safe, single shell commands. Anything else gets no decision, so the
# normal permission prompt is shown. Copilot denies the tool call if this hook fails, so it must always
# exit 0 and only ever print an "allow" decision.
#
# Options are checked against per-command allowlists rather than denylists: git and Gradle accept
# abbreviated and bundled options, so blocking known-dangerous flags by prefix is not reliable.

trap 'exit 0' ERR

command -v jq >/dev/null 2>&1 || exit 0

input="$(cat)"
cmd="$(jq -r '.tool_input.command // empty' <<< "$input" 2>/dev/null)" || exit 0
[[ -n "$cmd" ]] || exit 0

# Relative paths are only trusted inside the checkout, and the working directory can move with an approved "cd".
cwd="$(jq -r '.cwd // empty' <<< "$input" 2>/dev/null)" || exit 0
project="${CLAUDE_PROJECT_DIR:-$(cd "$(dirname "${BASH_SOURCE[0]}")/../.." && pwd)}"
[[ -n "$cwd" && ( "$cwd" == "$project" || "$cwd" == "$project"/* ) ]] || exit 0

# Reject chaining, pipes, redirection, substitution, expansion, quoting, globs, comments and multi-line commands.
# Quotes would hide options from the checks below, and globs could expand to option-like file names.
readonly UNSAFE_CHARS=$'[][;&|<>`$(){}\\\'"*?#]'
[[ "$cmd" =~ $UNSAFE_CHARS ]] && exit 0
[[ "$cmd" == *$'\n'* || "$cmd" == *$'\r'* ]] && exit 0

read -ra args <<< "$cmd"
set -- "${args[@]}"

# Succeeds when every option-like argument (starting with "-") fully matches the given regex.
options_match() {
    local pattern="^($1)$"
    shift
    local arg
    for arg in "$@"; do
        [[ "$arg" == -* && ! "$arg" =~ $pattern ]] && return 1
    done
    return 0
}

# Succeeds when every non-option argument fully matches the given regex.
positionals_match() {
    local pattern="^($1)$"
    shift
    local arg
    for arg in "$@"; do
        [[ "$arg" == -* || "$arg" =~ $pattern ]] || return 1
    done
    return 0
}

# Succeeds when no non-option argument points outside the working tree (absolute, home or drive-qualified
# paths). Without this, "git diff" silently
# switches to --no-index mode for outside paths and prints arbitrary files; "~" would expand to the home directory.
paths_in_repo() {
    local arg
    for arg in "$@"; do
        [[ "$arg" == -* ]] && continue
        [[ "$arg" == /* || "$arg" == '~'* || "$arg" =~ ^[A-Za-z]: || "$arg" =~ (^|/)\.\.(/|$) ]] && return 1
    done
    return 0
}

readonly HISTORY_OPTIONS='--|-p|-s|-c|-m|-w|-M|-C|-[0-9]+|-n[0-9]*|-U[0-9]+|--unified=[0-9]+|--max-count=[0-9]+'\
'|--stat|--shortstat|--numstat|--name-only|--name-status|--summary|--patch|--no-patch|--raw|--cached|--staged'\
'|--oneline|--graph|--decorate|--no-decorate|--all|--reverse|--merges|--no-merges|--first-parent|--follow'\
'|--abbrev-commit|--color|--no-color|--word-diff|--ignore-all-space|--ignore-space-change|--find-renames'\
'|--format=[^ ]*|--pretty=[^ ]*|--date=[a-z-]+|--since=[^ ]+|--until=[^ ]+|--after=[^ ]+|--before=[^ ]+'\
'|--author=[^ ]+|--grep=[^ ]+|-S[^ ]+|-G[^ ]+|-e|--numbered|--email'

is_safe_git() {
    [[ "$1" == "--no-pager" ]] && shift
    local sub="$1"
    shift

    case "$sub" in
        diff | log | show | shortlog)
            options_match "$HISTORY_OPTIONS" "$@" && paths_in_repo "$@"
            ;;
        status)
            options_match '-[sbv]+|--short|--branch|--long|--verbose|--porcelain(=v[12])?|--ignored'\
'|-u(no|normal|all)?|--untracked-files(=(no|normal|all))?' "$@"
            ;;
        blame)
            options_match '--|-w|-s|-e|-M|-C|-L[0-9,+-]+|--date=[a-z-]+|--show-email|--porcelain' "$@"
            ;;
        describe)
            options_match '--tags|--all|--always|--long|--dirty|--abbrev=[0-9]+' "$@"
            ;;
        rev-parse)
            options_match '--|-q|--quiet|--verify|--short(=[0-9]+)?|--abbrev-ref|--symbolic-full-name'\
'|--show-toplevel|--git-dir|--is-inside-work-tree' "$@"
            ;;
        merge-base)
            options_match '--all|--is-ancestor|--fork-point' "$@"
            ;;
        ls-files)
            options_match '--|-[cdmosuiz]+|--cached|--deleted|--modified|--others|--stage|--unmerged'\
'|--ignored|--exclude-standard' "$@"
            ;;
        check-ignore)
            options_match '--|-v|-n|--verbose|--non-matching|--no-index' "$@"
            ;;
        show-ref)
            options_match '-s|--heads|--tags|--head|--verify|--hash' "$@"
            ;;
        grep)
            options_match '--|-[nilLcwvEFhHI]+|-e|-[ABC][0-9]*|--line-number|--ignore-case|--files-with-matches'\
'|--count|--word-regexp|--invert-match|--extended-regexp|--fixed-strings|--cached|--untracked|--heading'\
'|--break|--and|--or|--not|--all-match|--context=[0-9]+|--after-context=[0-9]+|--before-context=[0-9]+'\
'|--max-depth=[0-9]+' "$@"
            ;;
        fetch)
            # Positionals are plain remote/branch names; refspecs (with ":" or a leading "+") can overwrite branches.
            # --tags and --prune are excluded: they can create or delete local tags.
            options_match '-q|-v|--all|--no-tags|--quiet|--verbose|--dry-run' "$@" \
                && positionals_match '[A-Za-z0-9._/-]+' "$@"
            ;;
        branch)
            options_match '-a|-r|-v|-vv|--all|--remotes|--list|--show-current|--no-color|--format=[^ ]*' "$@" \
                || return 1
            # A positional argument creates a branch unless it is a pattern for --list.
            local arg
            for arg in "$@"; do
                [[ "$arg" == -* ]] || { [[ " $* " == *" --list "* ]] && return 0 || return 1; }
            done
            return 0
            ;;
        remote)
            [[ $# -eq 0 || "$*" == "-v" ]]
            ;;
        *)
            return 1
            ;;
    esac
}

is_safe_gh() {
    case "$1 $2" in
        "pr view" | "pr diff" | "pr list" | "pr checks" | "pr status" | "issue view" | "issue list" \
            | "run view" | "run list" | "repo view")
            shift 2
            # Exclude --web, which opens a browser.
            options_match '-R|--repo=[^ ]+|-c|--comments|--json|--limit=[0-9]+|-L|--state=[a-z]+|-s|--patch'\
'|--name-only|--log|--log-failed|--color=[a-z]+|--branch=[^ ]+|-b|--author=[^ ]+|-A|--label=[^ ]+|-l' "$@"
            ;;
        *)
            return 1
            ;;
    esac
}

# Only build, test and inspection tasks. Gradle matches task names case-insensitively and expands camel-case
# abbreviations (e.g. "lintF" runs lintFix), so the allowlist must be exact: no open-ended suffix may follow a
# prefix that other tasks share. Release assembly is excluded because it signs the APK and can upload
# Crashlytics mapping files; lintFix is excluded because it edits sources.
readonly GRADLE_TASK='(:?[A-Za-z0-9_-]+:)*(assembleDebug[A-Za-z0-9]*|test[A-Za-z0-9]*|compile[A-Za-z0-9]*'\
'|lint|lintDebug|lintRelease|lintVital[A-Za-z0-9]*|lintAnalyze[A-Za-z0-9]*|check|tasks|dependencies|projects|help)'
readonly GRADLE_OPTIONS='-q|-i|--quiet|--info|--stacktrace|--offline|--continue|--no-daemon|--rerun-tasks|--parallel'\
'|--build-cache|--no-build-cache|--dry-run|--console=(plain|rich|auto|verbose)|--warning-mode=(all|fail|summary|none)'

is_safe_gradle() {
    local arg
    for arg in "$@"; do
        if [[ "$arg" == -* ]]; then
            [[ "$arg" =~ ^($GRADLE_OPTIONS)$ ]] || return 1
        else
            [[ "$arg" =~ ^($GRADLE_TASK)$ ]] || return 1
        fi
    done
    return 0
}

is_safe() {
    case "$1" in
        ./gradlew) shift; is_safe_gradle "$@" ;;
        git) shift; is_safe_git "$@" ;;
        gh) shift; is_safe_gh "$@" ;;
        ls) paths_in_repo "$@" ;;
        pwd) return 0 ;;
        *) return 1 ;;
    esac
}

if is_safe "$@"; then
    reason="Allowed by .claude/hooks/allow-safe-commands.sh"
    # Copilot reads the top-level fields; Claude Code reads hookSpecificOutput.
    jq -cn --arg reason "$reason" '{
        permissionDecision: "allow",
        permissionDecisionReason: $reason,
        hookSpecificOutput: {
            hookEventName: "PreToolUse",
            permissionDecision: "allow",
            permissionDecisionReason: $reason
        }
    }'
fi

exit 0
