#!/usr/bin/env bash
# PreToolUse hook shared by Claude Code and GitHub Copilot CLI (both read .claude/settings.json).
# Auto-approves a small allowlist of safe, single shell commands. Anything else gets no decision, so the
# normal permission prompt is shown. Copilot denies the tool call if this hook fails, so it must always
# exit 0 and only ever print an "allow" decision.

trap 'exit 0' ERR

command -v jq >/dev/null 2>&1 || exit 0

cmd="$(jq -r '.tool_input.command // empty' 2>/dev/null)" || exit 0
[[ -n "$cmd" ]] || exit 0

# Reject chaining, pipes, redirection, substitution, expansion and multi-line commands.
[[ "$cmd" =~ [\;\&\|\<\>\`\$\(\)\{\}\\] ]] && exit 0
[[ "$cmd" == *$'\n'* || "$cmd" == *$'\r'* ]] && exit 0

read -ra args <<< "$cmd"
set -- "${args[@]}"

is_safe_git() {
    [[ "$1" == "--no-pager" ]] && shift
    local sub="$1"
    shift

    case "$sub" in
        status | diff | log | show | blame | shortlog | describe | rev-parse | merge-base | ls-files \
            | check-ignore | show-ref | grep | fetch)
            local arg
            for arg in "$@"; do
                [[ "$arg" =~ ^(--output|-o$|-O|--open-files-in-pager|--upload-pack|--exec|-c$|--config) ]] \
                    && return 1
            done
            return 0
            ;;
        branch)
            local arg
            for arg in "$@"; do
                [[ "$arg" =~ ^(-a|-r|-v|-vv|--all|--remotes|--list|--show-current|--no-color|--format=.*)$ ]] \
                    || return 1
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
            return 0
            ;;
        *)
            return 1
            ;;
    esac
}

is_safe() {
    case "$1" in
        ./gradlew) return 0 ;;
        git) shift; is_safe_git "$@" ;;
        gh) shift; is_safe_gh "$@" ;;
        ls | pwd) return 0 ;;
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
