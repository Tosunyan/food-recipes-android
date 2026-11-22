---
name: investigate-issue
description: >-
  Investigate a bug, visual glitch or performance issue on a real device: reproduce, measure,
  fix and prove the fix with before/after evidence.
---

# Investigate Issue

Use for bugs, jank, layout shifts and other runtime issues ("it lags", "it jumps", "it crashes").
The rule: **no assumptions without device evidence, no fix without before/after proof.**

## 1. Prepare

- Start from a fresh branch off the latest `origin/main` (see `commit-and-push`), named
  `fix/<short-kebab-name>`. Never investigate on an unrelated feature branch. If there are
  uncommitted changes, ask the user before stashing or moving them.
- Write down the **success condition** and how it will be measured before touching code (e.g.
  "category cells don't resize after load", "janky frames < 1%").
- Read the relevant code, but treat roadmap notes and your own reading as hypotheses: they can be
  wrong (e.g. a suggested 1:1 ratio for images that are actually 320x200).

## 2. Reproduce On The Device

- `adb devices` to find the device; `./gradlew app:installDebug` to install.
- Record the device state you change (`adb shell getprop debug.layout`, animation scales, etc.)
  and restore it at the end.
- Make the issue reproducible. For an empty image cache clear only the cache:
  `adb shell "run-as com.tosunyan.foodrecipes sh -c 'rm -rf cache/*'"` (debuggable builds only).
  `adb shell pm clear com.tosunyan.foodrecipes` also deletes the Room database (saved meals), so
  run it only on a disposable install or with the user's explicit consent. Relaunch with
  `adb shell am force-stop com.tosunyan.foodrecipes` and
  `adb shell monkey -p com.tosunyan.foodrecipes -c android.intent.category.LAUNCHER 1`.
- Check the data, not just the code: fetch real API responses/images with `curl` (e.g. image
  dimensions with `file`) instead of guessing.

### Visual Issues (layout shifts, flicker)

- `screencap` is too slow for transitions. Record instead:
  `adb shell screenrecord --time-limit 10 /sdcard/x.mp4`, launch, then `adb pull`.
- Keep the recording long enough to cover splash and loading.
- Build a contact sheet with ffmpeg and view it:
  `ffmpeg -y -ss <s> -t <s> -i x.mp4 -vf "fps=8,crop=...,scale=260:-1,tile=8x2" -frames:v 1 s.png`.
- Layout bounds (`debug.layout`) help to see sizes; convert px to dp with `adb shell wm density`.

### Performance Issues (jank)

- Turn layout bounds off (`adb shell setprop debug.layout false` and
  `adb shell service call activity 1599295570`); they add draw cost.
- Measure with scripted input, not by feel: `adb shell dumpsys gfxinfo <pkg> reset`, repeated
  `adb shell input swipe ...`, then `adb shell dumpsys gfxinfo <pkg>` (janky frames %,
  p90/p95/p99, slow UI thread vs. GPU).
- **Debug builds are not representative.** Debuggable apps run with fewer runtime optimizations,
  and Compose is heavily affected. Compare with a non-debuggable build before optimizing code:
  temporarily set `isDebuggable = false` on the debug build type (revert afterwards), or use
  release if signing is available. Check both
  `adb shell cmd package compile -m speed-profile -f com.tosunyan.foodrecipes` and
  `-m verify` (fresh-install state).
- Run each measurement at least twice. Change one variable per experiment (e.g. remove
  `animateItem()`), measure, and revert if it doesn't help.

## 3. Fix

- Prefer the simplest fix that matches existing patterns (e.g. a fixed height like
  `DailySpecialItem` rather than a hardcoded ratio derived from today's backend data).
- If the evidence shows the code is fine (e.g. jank only in debuggable builds), say so instead of
  making speculative changes.
- Compile the affected modules.

## 4. Verify

- Repeat the exact reproduction from step 2 with the fix and compare against the baseline
  (side-by-side frames or a before/after metrics table).
- State what was **not** verified (other screen sizes, release signing, etc.).

## 5. Clean Up And Report

- Revert temporary build changes and experiments; `git status` must show only the fix.
- Restore device settings; delete recordings from `/sdcard` and temp files.
- Remove resolved items from `docs/ROADMAP.md`. Don't add findings, metrics or notes there;
  report them to the user instead.
- Report the root cause, evidence (numbers/frames), the fix and what remains unverified, then
  offer to commit via `commit-and-push`.
