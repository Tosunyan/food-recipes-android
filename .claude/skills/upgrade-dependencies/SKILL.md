---
name: upgrade-dependencies
description: Upgrade project dependencies in gradle/libs.versions.toml to the latest stable versions and validate the build.
---

# Upgrade Dependencies

## Rules

- Target the latest **stable** versions (skip `alpha`, `beta`, `rc`, `eap`). If a library only offers pre-releases or
  a stable version breaks the build, hold it back and document why in a comment above the version.
- Edit only the `[versions]` section of `gradle/libs.versions.toml`. When using `sed`, scope it to that section;
  an unscoped `^name = ` pattern also rewrites `[plugins]`/`[libraries]` aliases of the same name.
- Raising `compileSdk`/`targetSdk`, migrating to a new AGP major and similar fallout is in scope: do it rather than
  holding libraries back.
- Work on a `refactor/upgrade-dependencies` branch (see `commit-and-push`).

## Steps

1. Read the current versions in `gradle/libs.versions.toml` and the Gradle wrapper version.
2. Look up the latest versions from Maven metadata (`https://dl.google.com/dl/android/maven2/` and
   `https://repo1.maven.org/maven2/`, path `<group>/<artifact>/maven-metadata.xml`).
3. Pick versions supported by the Gradle wrapper (AGP 9.x needs Gradle 9.x); keep KSP and Kotlin compatible, and
   keep the Compose BOM and the explicit `compose` version aligned.
4. Apply the upgrades and build. Typical fallout:
   - Libraries requiring a higher `compileSdk` → raise `compileSdk` (all Android modules) and `targetSdk` in `app`.
   - AGP 9: remove the `org.jetbrains.kotlin.android` plugin (built-in Kotlin) from every module, the root build
     file and the catalog.
   - AGP 9 duplicate-namespace manifest errors from a library (e.g. Textflow ≥ 1.2.0) → use the newest version
     without the problem and comment why.
   - Use `./gradlew :app:checkDebugAarMetadata` and
     `./gradlew :<module>:dependencyInsight --dependency <name> --configuration debugRuntimeClasspath` to diagnose;
     transitive pulls (e.g. Ktor → OkHttp) may require holding the parent back.
5. Fix compile errors caused by API changes using the release notes.

## Acceptance Criteria

- `./gradlew app:assembleDebug test lint` succeeds (the same checks as CI).
- Only the catalog, build scripts and code required by the upgrade changed; no secrets staged.
- The summary lists upgraded versions (old → new), held-back dependencies with reasons, and migrations performed.

## Commit

Follow `commit-and-push`, e.g. headline `Upgrade dependencies`. Ask before pushing.
