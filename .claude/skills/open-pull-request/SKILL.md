---
name: open-pull-request
description: Open a GitHub pull request for the current branch (title, description, base branch, confirmation).
---

# Open Pull Request

## Preconditions

- The current branch is not `main` and all intended changes are committed (`git status --short` is clean).
- The branch is pushed and up to date with its remote. If not, push first via the `commit-and-push` skill,
  which requires the user's confirmation.
- No open PR exists for the branch yet. If one exists, offer to update its title/description instead.

## Base Branch

`main`, unless the user says otherwise; use the chosen `<base>` in every command below. Fetch it first so the
summary isn't based on a stale `origin/<base>`, then review what goes into the PR with:

```bash
git fetch origin <base>
git --no-pager log --oneline origin/<base>..HEAD
git --no-pager diff --stat origin/<base>...HEAD
```

## Title

PRs are squash-merged, so the title becomes the commit headline on `main` (GitHub appends ` (#<n>)`).
Follow the commit headline rules: one short, direct sentence in present tense, imperative, capitalized,
no trailing period, no type prefix. Example: `Set up GitHub Actions and update build configuration`.

## Description

Keep it short; an automated summary (CodeRabbit) is added to the PR separately.

```markdown
<1–2 sentences: what changes and why.>

* <Notable change>
* <Notable change>
```

- Bullets list notable changes, typically one per commit, phrased like commit headlines.
- Mention anything reviewers must know: manual setup, new `local.properties` keys, migrations, follow-ups.
- Link related issues (`Closes #<n>`) when applicable.
- No AI attribution or "Generated with" footers.

## Confirmation

**Always ask the user for confirmation before creating the PR.** Show the base and head branches, the title,
the description, and whether it is a draft (default: ready for review). Use the user's edits verbatim.

## Creating

Use the first available option:

1. GitHub CLI: `gh pr create --base <base> --head <branch> --title "<title>" --body-file <file>` (add `--draft`
   if requested). Pass the title as one shell-escaped argument (single quotes, with each embedded `'` written
   as `'\''`) so quotes and special characters in the confirmed title are preserved.
2. A GitHub MCP server connected to the repository's host (`github.com`).
3. Otherwise, give the user the compare URL to open the PR manually, along with the title and description:
   `https://github.com/<owner>/<repo>/compare/<base>...<branch>?expand=1` (derive `<owner>/<repo>` from
   `git remote get-url origin`).

Finish by sharing the PR URL.
