# Roadmap

## 1. Current Sprint
- **SnackBar component**
- **Saved-meal notification** – `depends-on: SnackBar component`. WIP: `feature/notificaiton`
- **Un-save confirmation** – `depends-on: SnackBar component`. Prefer an **Undo** action over a
  dialog.

## 2. Bugs
- **Unsaving leaves ingredients behind** – ingredient IDs are random UUIDs, so unsaving a freshly
  fetched meal (Home, Search, Meals list, fetched details) doesn't match the stored rows. Unsaving a
  `MealDetailsModel` that was saved from the same instance, or from the Saved tab, works. Add a
  foreign key with `onDelete = CASCADE` (index `meal_id`, bump the DB version) and delete by
  `mealId`.
- **Saving/unsaving isn't atomic** – meal and ingredients are inserted/deleted in two parallel
  `launch` calls. Wrap them in `withTransaction`.
- **Saved status goes stale** – each ViewModel keeps its own `isSaved`. Stale cases: Home (cached
  random meal, loaded once), Search (results never refreshed) and details opened from a details
  model. `MealsViewModel.onArgumentsReceive` re-runs on return and refetches the list from the API
  (saved IDs from Room), which hides the problem but costs a network reload. Expose saved IDs as a
  `Flow` and `combine` it per screen.
- **Duplicate screen keys** – every `MealsScreen` shares one key, and a `MealDetailsScreen` can be
  pushed twice for the same meal. Stacked `MealsScreen`s share one `MealsViewModel` because Voyager
  stores the ViewModel owner by `Screen.key`. Use unique keys (include category/region).
- **ViewModel leaks Navigator/Context** – both are held in state lambdas. Handle navigation and
  intents in the composable or via one-off events.
- **Search results arrive out of order** – each query launches an uncancelled coroutine. Use
  `mapLatest`/`collectLatest`.
- **Deep link handled again** – `IntentHandler` never consumes the intent, so config changes or
  relaunch from Recents can push the screen again. A `lastItem.key` guard in `IntentHandler` and the
  portrait lock reduce the risk. Clear `intent.data` after handling. WIP:
  `feature/deep-link-management`.
- **Shimmer never stops** – `isImageLoading` in `MealDetailsScreen` is a plain `var`. Use
  `remember { mutableStateOf(true) }` inside the `item {}` block (as `DailySpecialItem` does).
- **Unstable `LazyColumn` keys** – details items are keyed by content (name, thumbnail,
  instructions, ingredient objects, category + region, link URLs). Keys change when the content
  changes (e.g. the deep-link placeholder has an empty name) and can collide. Use constant keys
  (ingredients: index, as names may repeat).

## 3. Quality
### Architecture
- **Hidden errors** – `withIO*` turns failures into `null`/empty, so the UI can't tell offline from
  no results. Return `Result` or a sealed state. Also covers error handling and a no-network state;
  *Proper loading handling* (backlog) depends on it.
- **Global mutable cache** – `HomeRepository` caches in a `companion object`. Move it to the
  instance or to Room.
- **Database schema** – enable `exportSchema` and add a sample `Migration` with a test (showcase).
- **Release network logging** – `LoggingInterceptor` buffers and logs full bodies in release, and
  leaks the API key in the URL. Add it only when `BuildConfig.DEBUG`.
- **Release minification** – `isMinifyEnabled = false`. Enable R8 with keep rules.
- **UI types in `BottomNavigationViewModel`** – it builds `Painter`s from a `Context` on every tab
  change. Use a `NavigationTab` enum with resource IDs and resolve them in the composable. Pass
  app-link verification as a `Boolean`.
- **Inconsistent state APIs** – public `MutableStateFlow`s, `collectAsState` vs
  `collectAsStateWithLifecycle`, `viewModel()` vs `koinViewModel()`.
- **Build duplication** – shared config is repeated per module, `core:ui` has unused dependencies,
  and Compose artifacts are pinned over the BOM. `depends-on: Gradle convention plugins` for the
  duplication.
- **API client rotation** – `Random` per injection is effectively random per repository. Replace
  with an `ApiClientSelector` (one choice per session, logged). Later driven by Remote Config.
- **Logging** – replace `println` in repository `init` blocks with `logInfo`.

### Styling / UX
- **Keyboard covers results** – `adjustNothing` with no `imePadding()`. Use `adjustResize` +
  `imePadding()`.
- **Hardcoded strings** – `"Search"` (`SearchScreen`), `"MealImage"` (`MealDetailsScreen`). Move
  them to resources.
- **Save icons** – the layout bounds are about 16–20dp (`clickable` after inner padding) and there
  is no `contentDescription`/state description. Target 48dp.
- **Accessibility** – at least content descriptions. Overlaps with *Save icons*.

## 4. Integrations
- **Tests** – mappers, ViewModels, DAO. Start now, before the migrations. WIP:
  `feature/unit-testing`. CI already runs `./gradlew test`.
- **Gradle convention plugins** – cheap, and makes KMP easier.
- **CI/CD** – add lint, and don't ship a debug APK for production.
- **DataStore**
- **Remote Config** – also replaces the random client switch.
- **A/B Testing** – `depends-on: Remote Config`
- **Bugfender**
- **KtorFit** – a third network client for the showcase.

## 5. Migrations
- **Gradle Toolchain** – all modules already set `jvmToolchain(21)`; define what is still missing
  (e.g. toolchain resolver) or drop.
- **Navigation3** – replace Voyager. WIP: `feature/navigation3`. Decide together with *MVI* and the
  bottom-navigation restructuring.
- **Clean-ish architecture** – `depends-on: Tests`
- **MVI** – `depends-on: Clean-ish architecture, Tests`, and decide the bottom-navigation
  restructuring first.
- **Custom API** – needs a backend. Do it before KMP so DTOs are stable.
- **KMP (Android, iOS)** – `depends-on: Gradle convention plugins, Custom API`. Needs the Room KMP
  setup (bundled SQLite driver), Coil 3, Ktor only (drop Retrofit), Koin without Android-only APIs
  and others.

## 6. Backlog
**Needs a backend** (`depends-on: Custom API`)
- Authentication
- Account/registration
- Private recipes
- Internal rating
- Reviews
- My Notes
- Premium mode
- Push notifications
- Firebase function for the daily meal
- Referral system

**Data & state**
- Store *Categories*, *Regions*, *Daily Special* in Room (offline-first). A local alternative to the
  Firebase function: store `random.php` with today's date.
- Pull to refresh – `depends-on: Hidden errors`
- Force update – `depends-on: Remote Config`
- Proper loading handling (per substate) – `depends-on: Hidden errors`

**Search & lists**
- Search in *Saved Meals*, Search autocomplete, Recent searches
- Search by ingredient (`filter.php?i=`)
- Sorting (name, newest) – WIP: `feature/sort-filter`; Filtering (categories, regions, calendar,
  tags)
- Meal tagging (prep time, difficulty) – `depends-on: Custom API` (the current API has no such data)
- List sticky headers / multiple view types
- List/Grid view chooser
- "Recently Viewed" section
- Pin favorite recipes (3–5 max)

**Meal details**
- See more / see less – WIP: `feature/expandable-text`
- Remove extra spaces between rows (e.g. *Blini Pancakes*) – WIP: `feature/instructions-empty-lines`
- Thumbnail preview screen with transition – WIP: `feature/shared-transition`
- Show flags
- Personal comment
- Step-by-step cooking mode – split instructions on line breaks / `STEP n` (best effort)
- Shopping list (ref. Yummly) – local, built from saved ingredients

**Navigation & shell**
- Bottom navigation item re-select (scroll to top, clear state) – WIP: `feature/scroll-to-top`
- Tab transitions (stack transitions already use `SlideTransition`)
- Saved item long-click menu
- App shortcuts
- Onboarding
- Handle hovers (low priority, tablets/mice only)

**App-wide**
- Settings module (About app, About libraries)
- Dark theme – check design system support first, then palettes (coolors / Material Basil
  references)
- Tablet support
- Localization via APIs – cache translations (cost/latency)
- Log screens, navigation and actions – `depends-on: Bugfender`
- Contact support
- Birthday Special
- Google Calendar integration
- AI chatbot (ask for recipes)
- Spoonacular API (more features) – check rate limits and pricing
- Basil design reference (Material study)

## 7. Due to Validation
- Make ingredient values bold
- Show the bottom navigation on all screens – needs restructuring, since details/meals screens sit
  outside the tab navigator
- Badges on the app (new recipes, etc.)
- Text copy for *Cooking Process* and *Ingredients*

## 8. API Limitations
- "Cooking type" label (oven, microwave, etc.)
- "How To" and "Tips" sections (e.g. *Blini Pancakes*)
- Referral system, bonus 10 recipes – `depends-on: Custom API` (also listed in the backlog)
