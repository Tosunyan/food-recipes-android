---
name: commit-and-push
description: Commit and push changes following project conventions (branches, message format, push confirmation).
---

# Commit And Push

## Branches

- Never commit directly to `main`.
- Naming: `feature/<short-kebab-name>`, `refactor/<name>` or `fix/<name>`.
- Create new branches from the latest `origin/main`:
  `git fetch origin && git switch -c <branch> origin/main`, then `git branch --unset-upstream` so the branch
  does not track `main`.

## Before Committing

1. Review `git status --short` and `git --no-pager diff`; stage only files related to the task — no unrelated
   changes, build outputs, or IDE files.
2. Never stage `local.properties`, `prod.properties`, keystore passwords, or any other secrets.
3. Make sure the affected modules compile (e.g. `./gradlew :feature:main:compileDebugKotlin`).

## Commit Message

- **Headline**: one short, direct sentence in present tense, imperative, capitalized, no trailing period,
  no type prefix. Examples from history: `Filter duplicate regions from API`,
  `Extract :feature:main module from core/ui`, `Upgrade compile & target SDK to 36`.
- **Description** (optional): one paragraph of 1–2 sentences with extra context — no more.
- **Never** add AI co-author trailers (e.g. `Co-authored-by: Copilot`, `Co-Authored-By: Claude`) or
  "Generated with" footers.
- One logical change per commit; split unrelated changes.

## Committing

Committing is local and easy to undo, so it needs no confirmation (only pushing does). Commit, then show the
user the staged files and the resulting headline and description. If the user asks for a different message,
use their version verbatim with `git commit --amend` (only while the commit is unpushed).

```bash
git commit -m "Add ingredient filter to search" -m "Allows filtering search results by main ingredient."
```

## Pushing

- **Always ask the user for explicit confirmation before `git push`**, showing the branch name and the commits
  to be pushed (`git --no-pager log --oneline origin/main..HEAD`).
- First push of a branch: resolve the name with `git branch --show-current`, make sure it is not `main` and is
  valid (`git check-ref-format --branch`), confirm that exact name, then run `git push -u origin -- "$branch"`.
- Never force-push to `main`. Force-pushing a feature branch (`--force-with-lease`) also needs confirmation.
- After pushing a branch that has no open PR, offer to open one via the `open-pull-request` skill.
