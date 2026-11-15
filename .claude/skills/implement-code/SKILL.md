---
name: implement-code
description: Implement a task or roadmap item through a multi-agent plan, review, implement and commit chain.
---

# Implement Code

Multi-agent development chain for one task: a roadmap item (`docs/ROADMAP.md`, which may live on another
branch, e.g. `feature/roadmap`) or a free-form request. The main agent orchestrates; the work is done by
subagents with fresh context.

Runs only when the user asks to implement something. Do one task per run; if the request bundles several,
propose how to split it and confirm.

## Models

Planning quality decides everything downstream, so spend the capability there:

- **Planner and plan reviewer**: frontier-level models. The plan must be detailed enough (files, signatures,
  ordered steps) that a cheaper model can implement it without design decisions.
- **Implementer**: a mid-tier model by default, to save tokens. Use a frontier-level model when the plan marks
  an area as high-risk (concurrency, Room schema, navigation, security) or when a fix round has failed.
- **Code reviewers**: frontier-level, as defined in `review-code`.

Pick the most capable available model for each tier; don't hardcode model names.

## Checkpoints

The user has two mandatory checkpoints (besides questions the skill explicitly allows):

1. **Plan approval** — before any code is written.
2. **Push approval** — after the summary.

Committing locally needs no confirmation (see `commit-and-push`). Pushing always needs explicit confirmation.

## 1. Analyze

Resolve the task before planning:

- Read the roadmap entry (including `depends-on` notes and related items) and the code it touches.
- If a dependency is not done yet, stop and tell the user instead of working around it.
- If the task is vague, define the specifics yourself: scope, behavior, edge cases, what is explicitly out of
  scope. Write these down as **assumptions** so the user can correct them at the plan checkpoint.
- Ask the user only when a decision is expensive to reverse or can't be inferred from the code and
  `CLAUDE.md`.

## 2. Plan

Spawn a **planner** subagent with the task, the assumptions and a pointer to `CLAUDE.md`. It returns:

- goal and assumptions,
- files/modules to change or add, following the module boundaries and conventions in `CLAUDE.md`,
- ordered implementation steps,
- risks, alternatives considered and open questions,
- how it will be verified (compile commands; tests if the change warrants them).

The planner never edits files.

## 3. Review The Plan

Spawn a **plan reviewer** subagent (fresh context; give it the task and plan, not the planner's reasoning). It
checks that the plan satisfies the task, fits the architecture, has no missing steps or hidden risks, and
isn't bigger than needed. Verdict: `APPROVED` or `CHANGES REQUESTED` with concrete findings.

Repeat steps 2–3 (planner revises, a new reviewer reviews) until `APPROVED`, at most **3** rounds. If still
not approved, present the plan with the unresolved findings.

## 4. Plan Approval

Show the user the final plan: assumptions, steps, files, risks, open questions, and what the reviewer raised.
**Wait for explicit confirmation.** If the user changes the plan, update it and re-run step 3 for substantial
changes. Don't start implementing before approval.

## 5. Implement

- Make sure you are on a suitable branch: never `main`; create `feature/<name>`, `refactor/<name>` or
  `fix/<name>` from the latest `origin/main` as described in `commit-and-push`. Don't carry over unrelated
  changes from the current branch.
- Spawn an **implementer** subagent (model tier per **Models**) with the approved plan. It follows `CLAUDE.md`
  and the relevant project skills (`add-feature-screen`, `add-api-endpoint`), compiles the affected modules, and
  does not commit.
- The implementer reports what it did, deviations from the plan with reasons, blockers and open questions.
  Don't let it silently expand scope; extra findings go to the summary instead.

## 6. Review And Commit

1. Run the **Review Gate** from `.claude/skills/review-code/SKILL.md` on the local changes (two independent
   reviewers, both must `PASS`, max 3 rounds).
2. Fixes between rounds are made by an implementer subagent with the findings, not by the reviewers.
3. If the gate still fails after its rounds, don't commit. Go to step 7 and report the remaining findings.
4. Otherwise commit locally following `commit-and-push` (message format, no AI co-author, no secrets, one
   logical change per commit). **Do not push.**

## 7. Summarize And Confirm Push

Follow `.claude/skills/summarize-changes/SKILL.md` to present the result, then **ask for explicit
confirmation before `git push`**, showing the branch and commits. Offer to open a PR via `open-pull-request`
only after the push.

## Rules

- Subagents get fresh context: the task, the relevant artifact (plan or diff) and instructions, not the
  conversation history.
- Reviewers never modify code; planners never modify files.
- Don't mark roadmap items done or edit `docs/ROADMAP.md` unless the user asks.
- If anything unexpected makes the plan invalid mid-implementation, stop and report to the user.
