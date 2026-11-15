---
name: upgrade-dependencies
description: Upgrade dependencies in gradle/libs.versions.toml to the latest stable versions and validate the build.
---

# Upgrade Dependencies

## Rules

- Target the latest **stable** versions (skip `alpha`, `beta`, `rc`, `eap`). If a library only offers pre-releases or
  a stable version breaks the build, hold it back and document why in a comment above the version.
- Edit only the `[versions]` section of `gradle/libs.versions.toml`. When using `sed`, scope it to that section;
  an unscoped `^name = ` pattern also rewrites `[plugins]`/`[libraries]` aliases of the same name.
- Fallout from the upgrade (raising `compileSdk`/`targetSdk`, plugin or build-script migrations required by a new
  major version) is in scope: do it rather than holding libraries back, unless the user says otherwise.
- Keep the SDK levels mentioned in `CLAUDE.md` in sync.
- Work on a `refactor/upgrade-dependencies` branch (see `commit-and-push`).

## Steps

1. Read the current versions in `gradle/libs.versions.toml` and the Gradle wrapper version.
2. Look up the latest versions from Maven metadata (`https://dl.google.com/dl/android/maven2/` and
   `https://repo1.maven.org/maven2/`, path `<group>/<artifact>/maven-metadata.xml`).
3. Pick versions compatible with each other and with the Gradle wrapper (check the AGP release notes for the
   required Gradle version); keep KSP and Kotlin compatible, and keep the Compose BOM and the explicit `compose`
   version aligned.
4. Apply the upgrades and build. Diagnose failures with:
   - `./gradlew :app:checkDebugAarMetadata` for libraries requiring a newer `compileSdk` or AGP;
   - `./gradlew :<module>:dependencyInsight --dependency <name> --configuration debugRuntimeClasspath` for
     transitive version pulls (the parent library may need to be held back);
   - `--info` output and the manifest merger report for manifest errors;
   - the release notes / migration guide of every major-version bump (build plugins, removed APIs).
5. Fix the fallout using the release notes. If a library cannot be upgraded, use the newest version that works and
   comment why.

## Acceptance Criteria

- `./gradlew app:assembleDebug test lint` succeeds (the same checks as CI).
- Only the catalog, build scripts, docs and code required by the upgrade changed; no secrets staged.
- The summary lists upgraded versions (old → new), held-back dependencies with reasons, and migrations performed.

## Commit

Follow `commit-and-push`, e.g. headline `Upgrade dependencies`. Ask before pushing.
