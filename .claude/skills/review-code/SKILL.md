---
name: review-code
description: Review local changes or a GitHub pull request with two independent reviewers that both must pass.
---

# Review Code

Code quality matters in this project: review correctness **and** style/formatting. Don't skip small issues.

This skill has two parts:

- **Review Gate** — what the main agent does when the user asks for a review.
- **Single Review** (steps 1–4) — what each individual reviewer does.

## Review Gate

Runs only when the user asks for a review (not automatically after changes or before commits).

1. **Spawn two reviewers in parallel** as separate subagents with fresh context. Give each the same prompt:
   the review target (local changes or PR number) and the instruction to perform the **Single Review** from
   this file (`.claude/skills/review-code/SKILL.md`), report findings in the format of step 4, end with a
   verdict of `PASS` or `CHANGES REQUESTED`, and not spawn further reviewers.
2. **Keep them independent**: don't pass the implementer's reasoning, the conversation history, or the other
   reviewer's findings. Each reviewer covers the full scope.
3. **Consolidate** both reports: merge duplicates and note which reviewer(s) raised each finding.
4. **Satisfied** means both reviewers return `PASS` — no findings left, except ones the user explicitly waived.
5. **Iterate** (local changes only):
   - Fix all findings, then run a **new** pair of reviewers on the updated changes. Tell them which findings
     the user waived, so they aren't re-raised.
   - Maximum **2** rounds. If either reviewer still requests changes after round 2, stop and present the
     remaining findings to the user to fix, waive, or discuss.
   - If a finding is wrong or conflicts with the user's intent, don't silently ignore it — ask the user to
     waive it.
6. **Pull requests**: report the consolidated findings only; don't modify code or post comments on GitHub
   unless the user explicitly asks to.
7. **Final summary**: rounds run, findings fixed, findings waived, and the final verdict of each reviewer.

## Single Review

### 1. Get The Diff

- **Local changes**: `git --no-pager diff HEAD` (uncommitted) and, for the whole branch,
  `git --no-pager diff origin/main...HEAD`. Include untracked files from `git status --short`.
- **Pull request**: use the available GitHub tooling (GitHub MCP server, or `gh pr diff <n>` / `gh pr view <n>`).
  Fallback: `git fetch origin pull/<n>/head:pr-<n>` and diff against `origin/main`. Also read the PR
  description and existing review comments.

Read the full surrounding code of changed files — don't judge from diff hunks alone.

### 2. What To Check

**Correctness**
- Logic bugs, edge cases, null handling, wrong mapping between `*Dto` / `*Entity` / `*Model`.
- Coroutines: IO goes through `DispatcherScope` helpers (`withIOResultOrNull`, `ioFlow`, ...); nothing blocks
  Main; work is launched in `viewModelScope` or another lifecycle-bound scope.
- State: `ScreenState` updated via `_screenState.update { it.copy(...) }`; no mutable state exposed publicly.

**Architecture** (see `CLAUDE.md`)
- Module boundaries: features use repositories from `core:data`, never `core:network` / `core:database`;
  `core:model` stays pure Kotlin.
- New endpoints are in `ApiService` **and** `KtorClient`, and both clients behave identically.
- New ViewModels/repositories/helpers are registered in the correct Koin module.
- Room entity changes bump `MealDatabase.version`.

**Compose / UI**
- Colors and typography from `AppTheme`, no hardcoded values; user-facing strings in `strings.xml`.
- Stateless composables with hoisted state; no business logic in composables.
- Stable `key`s for lazy list items; previews wrapped in `FoodRecipesTheme`.

**Code Quality**
- Readability: clear names, small focused functions, no deep nesting, no duplication.
- No dead code, unused parameters, leftover logs/`println`, commented-out code, or unresolved TODOs.
- No magic numbers or strings — use named constants or resources.
- Idiomatic Kotlin: `val` over `var`, immutable collections, expression bodies where clearer, scope functions
  used sensibly, no `!!` without justification.
- Visibility as narrow as possible (`private` / `internal`).
- Comments explain *why*, not *what*; KDoc for non-obvious public APIs.

**Formatting & Style**
- Kotlin coding conventions and consistency with surrounding code.
- Lines within 120 characters (code and Markdown).
- Indentation, blank lines and spacing consistent; no trailing whitespace; files end with a newline.
- Trailing commas in multi-line parameter/argument lists, matching existing code.
- No unused or wildcard imports.
- Named arguments for calls with several parameters of the same type or boolean flags.

**Build & Security**
- Dependencies added through `gradle/libs.versions.toml`.
- No secrets, keystore passwords, API keys, or `*.properties` files committed.

### 3. Verify

Compile the affected modules (e.g. `./gradlew :feature:main:compileDebugKotlin`), or run
`./gradlew app:assembleDebug` for broad changes. Use IDE diagnostics/inspections when available.

### 4. Report

Group findings by severity — **Bug**, **Design**, **Quality**, **Style/Formatting** — each with `file:line`,
the problem, and a concrete fix. If nothing is found in a category, omit it.
End with a verdict: `PASS` (no findings) or `CHANGES REQUESTED`.
As a reviewer, never modify code or post comments on GitHub.
