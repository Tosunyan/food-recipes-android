---
name: add-feature-screen
description: Add a new Compose screen or bottom navigation tab (Voyager Screen/Tab, ViewModel, Koin, preview).
---

# Add Feature Screen

Creates a screen following the existing patterns in `feature:main`. Use `screens/meals/` as the reference
implementation.

## 1. Decide The Screen Kind

- **Pushed screen** (opened via navigation, may take arguments): `class XScreen(...) : Screen`.
- **Bottom navigation tab**: `class XScreen : Tab` with `override val options: TabOptions`
  (see `screens/home/HomeScreen.kt`), and register it in `screens/bottomnavigation/BottomNavigationViewModel.kt`
  as a new `BottomNavigationItem`.

## 2. Create Files

Location: `feature/main/src/main/kotlin/com/tosunyan/foodrecipes/main/screens/<name>/`

- `<Name>Screen.kt`
- `<Name>ViewModel.kt`

### Screen

- Public `@Composable override fun Content()`:
  - `val viewModel = koinViewModel<XViewModel>()`
  - `val navigator = LocalNavigator.currentOrThrow` (if navigating)
  - Pass arguments to the ViewModel with `LaunchedEffect(args) { viewModel.onArgumentsReceive(...) }`
  - Delegate to a private stateless `Content(state, onXClick = ..., ...)`.
- Private stateless `Content(...)`: takes `ScreenState` plus callbacks with `= {}` defaults.
- For screens with constructor args, override `key` (`this::class.simpleName!!`) and add a no-arg secondary
  constructor annotated `@Suppress("unused", "PrivatePreviews")`, as in `MealsScreen`.
- A private `@Preview(showBackground = true)` composable wrapped in `FoodRecipesTheme { }`.
- Styling only via `AppTheme.colorScheme.*` and `AppTheme.typography.*`; reuse components from `core:ui`
  (`Toolbar`, list items) and `feature:main/components`.
- Navigation: `navigator.push(OtherScreen(arg))`, `navigator.pop()`.

### ViewModel

```kotlin
class XViewModel(
    private val repository: SomeRepository,
) : ViewModel() {

    private val _screenState = MutableStateFlow(ScreenState())
    val screenState = _screenState.asStateFlow()

    fun onSomethingClick() {
        viewModelScope.launch {
            _screenState.update { it.copy(...) }
        }
    }

    data class ScreenState(
        val isLoading: Boolean = true,
    )
}
```

- Depend on repositories from `core:data` and helpers (e.g. `MealSavingHelper`), never on `ApiService`, DAOs or
  `MealDatabase`.
- For DB-backed streams, expose a `StateFlow` with
  `stateIn(viewModelScope, SharingStarted.WhileSubscribed(5.seconds), initial)` (see `SavedMealsViewModel`).
- Use domain `*Model` types only.

## 3. Register In Koin

Add `viewModelOf(::XViewModel)` to `feature/main/.../main/MainModule.kt`.

## 4. Strings And Resources

User-facing text goes into `feature/main/src/main/res/values/strings.xml`; drawables into
`feature/main/src/main/res/drawable/`.

## 5. Verify

```bash
./gradlew :feature:main:compileDebugKotlin
```
