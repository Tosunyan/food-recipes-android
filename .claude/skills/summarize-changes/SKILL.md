---
name: summarize-changes
description: >-
  Summarize completed work for the user (what is done, blockers, open questions, next steps).
---

# Summarize Changes

Use at the end of a task (e.g. from `implement-code`) or when the user asks what changed. Base it on
facts (run `git fetch origin main` first): `git --no-pager log --oneline origin/main..HEAD`,
`git --no-pager diff --stat origin/main...HEAD`, plus `git status --short` and
`git --no-pager diff HEAD --stat` for staged, unstaged and untracked changes that aren't committed
yet, the plan, and the review results — not on memory of what was intended.

## Format

Keep it scannable; no filler.

1. **Task** — one line: what was requested and the final status (`Done`, `Partially done`,
   `Blocked`).
2. **What changed** — grouped by module or concern, with key files. Mention behavior changes, new
   dependencies and Room/API changes explicitly.
3. **Commits** — hash and headline of each local commit, and whether they are pushed.
4. **Verification** — what was compiled/tested and the result; what was *not* verified (e.g. not run
   on a device) so the user knows what to check manually.
5. **Review** — rounds run, findings fixed, findings waived or still open, final verdict of each
   reviewer.
6. **Deviations from the plan** — what differs and why.
7. **Blockers** — anything that stopped or limited the work, with the cause.
8. **Open questions** — decisions the user must make, each with a recommended option.
9. **Follow-ups** — related work noticed but deliberately left out (e.g. roadmap items it unblocks).

Omit empty sections except Task, Commits and Verification. Say plainly if something failed or was
skipped; never present partial work as complete.

## After The Summary

If there are unpushed commits, ask for explicit confirmation before `git push`, showing the branch
and the commits (see `commit-and-push`).
