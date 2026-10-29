---
name: commit-and-push
description: Commit and push changes following project conventions (branches, message format, confirmations).
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

**Always ask the user for confirmation before committing.** Show:

- the list of staged files,
- the proposed headline and description.

The user may edit the message — use their version verbatim. Then commit:

```bash
git commit -m "Add ingredient filter to search" -m "Allows filtering search results by main ingredient."
```

## Pushing

- **Always ask the user for explicit confirmation before `git push`**, showing the branch name and the commits
  to be pushed (`git --no-pager log --oneline origin/main..HEAD`).
- First push of a branch: `git push -u origin <branch>`.
- Never force-push to `main`. Force-pushing a feature branch (`--force-with-lease`) also needs confirmation.
- After pushing a branch that has no open PR, offer to open one via the `open-pull-request` skill.
