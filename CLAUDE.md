# Food Recipes (Android)

Portfolio/reference Android app showing recipes from [TheMealDB API](https://www.themealdb.com/api.php).
Written in Kotlin with Jetpack Compose, multi-module, MVVM.

This file is the single source of AI instructions for both Claude Code and GitHub Copilot.
Do not add `AGENTS.md` or `.github/copilot-instructions.md` — Copilot already reads this file.

## Tech Stack

- Kotlin 2.x, JVM toolchain 21, `minSdk` 26, `compileSdk`/`targetSdk` 36
- UI: Jetpack Compose + custom design system (`com.github.inconcept:android-design-system`, accessed via `AppTheme`)
- Navigation: Voyager (`Screen`, `LocalNavigator`, tab navigator)
- DI: Koin (`module { }`, `singleOf`, `viewModelOf`, `koinViewModel()`)
- Network: Retrofit **and** Ktor, both implementing the same `ApiService` interface; kotlinx.serialization
- Persistence: Room (KSP)
- Images: Coil; Firebase Crashlytics/Analytics in `app`

## Module Map

| Module          | Type        | Responsibility                                                      |
|-----------------|-------------|---------------------------------------------------------------------|
| `app`           | Android app | `Application`, `MainActivity`, Koin startup, root `Navigator`       |
| `feature:main`  | Android lib | All screens, ViewModels, feature components, `MainModule`           |
| `core:ui`       | Android lib | `FoodRecipesTheme`, shared composables; re-exports Compose, Voyager |
| `core:data`     | Android lib | Repositories, DTO/Entity ↔ Model mappers, `RepositoryModule`        |
| `core:network`  | Android lib | `ApiService`, Retrofit/Ktor clients, DTOs, `NetworkModule`          |
| `core:database` | Android lib | Room `MealDatabase`, DAOs, entities, `DatabaseModule`               |
| `core:model`    | Kotlin/JVM  | Plain domain models (`*Model`), no Android deps                     |
| `core:common`   | Android lib | Coroutine helpers (`DispatcherProvider`, `DispatcherScope`), utils  |
| `core:di`       | Kotlin/JVM  | Exposes Koin dependencies via `api`                                 |

Dependency direction: `app` → `feature:*` → `core:ui` / `core:data` → `core:network` / `core:database` →
`core:model` / `core:common` / `core:di`.
Features must not depend on `core:network` or `core:database` directly — go through repositories in `core:data`.

## Conventions

- **Packages**: `com.tosunyan.foodrecipes.<module>...`; sources live in `src/main/kotlin`.
- **Naming**: network `*Dto`, Room `*Entity`, domain `*Model`, Koin modules `PascalCase` vals (e.g. `MainModule`).
- **Mappers**: top-level extension functions in `core:data/.../mappers` (e.g. `MealDto.toMealModel()`).
- **Screens** (`feature:main/.../screens/<name>/`):
  - `class XScreen(args) : Screen` with a public `Content()` that obtains the ViewModel via `koinViewModel()` and
    the navigator via `LocalNavigator.currentOrThrow`, then delegates to a private stateless
    `Content(state, callbacks…)`.
  - Add a `@Preview` wrapped in `FoodRecipesTheme`.
  - Use `AppTheme.colorScheme` / `AppTheme.typography` for styling — no hardcoded colors.
- **ViewModels**: `private val _screenState = MutableStateFlow(ScreenState())` +
  `val screenState = _screenState.asStateFlow()`; update with `_screenState.update { it.copy(...) }`;
  `ScreenState` is a nested `data class`. Register with `viewModelOf(::XViewModel)` in `MainModule`.
- **Repositories**: implement `DispatcherScope`, take `DispatcherProvider` in the constructor, and wrap IO with
  `withIOResultOrNull` / `withIOResultOrDefault` / `ioFlow`. Register with `singleOf` in `RepositoryModule`.
- **New API endpoints**: add to `ApiService` with Retrofit annotations (`RetrofitClient` delegates via
  `retrofit.create()`) **and** implement manually in `KtorClient`.
- **Room**: database `version = 1` with destructive fallback migration; bump the version when changing entities.
- **Dependencies**: always add via `gradle/libs.versions.toml` and reference as `libs.*`; reference modules with
  type-safe accessors (`projects.core.data`).
- **Formatting**: follow the Kotlin coding conventions; keep lines (code and Markdown) within 120 characters.

## Build & Verify

```bash
./gradlew app:assembleDebug   # build (same as CI)
./gradlew test                # unit tests (same as CI)
./gradlew :feature:main:compileDebugKotlin   # fast check for a single module
```

There are currently no unit or instrumentation tests; verify changes by compiling the affected modules.

## Local Setup & Secrets

- Requires `local.properties` with `KEY_ALIAS`, `KEY_PASSWORD`, `STORE_PASSWORD`, `MEAL_API_KEY`, `MEAL_API_URL`
  (see `README.md`). Values can also come from environment variables.
- Release signing reads `prod.properties`. All `*.properties` files are git-ignored — never commit them or any
  secrets.
- `MEAL_API_URL` / `MEAL_API_KEY` are exposed via `BuildConfig` in `core:network`.

## AI Tooling

Shared by Claude Code and GitHub Copilot CLI — keep everything in the Claude locations, without duplicates:

- Skills: `.claude/skills/<name>/SKILL.md`.
- Safe commands: `.claude/settings.json` registers a `PreToolUse` hook, `.claude/hooks/allow-safe-commands.sh`,
  that auto-approves an allowlist of single commands (`./gradlew`, read-only `git`/`gh`, `ls`, `pwd`).
  Everything else is prompted. Extend the allowlist there; never allow commits, pushes, or destructive commands.
