package com.tosunyan.foodrecipes.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.inconceptlabs.designsystem.components.core.Text
import com.inconceptlabs.designsystem.components.emptyitem.EmptyItem
import com.inconceptlabs.designsystem.components.emptyitem.EmptyItemData
import com.inconceptlabs.designsystem.components.input.InputForm
import com.inconceptlabs.designsystem.theme.AppTheme
import com.inconceptlabs.designsystem.utils.clearFocusOnGesture
import com.tosunyan.foodrecipes.model.MealDetailsModel
import com.tosunyan.foodrecipes.ui.R
import com.tosunyan.foodrecipes.ui.components.meals.MealDetailsList
import com.tosunyan.foodrecipes.ui.mealdetails.MealDetailsScreen
import com.tosunyan.foodrecipes.ui.theme.FoodRecipesTheme
import com.tosunyan.foodrecipes.ui.viewmodel.SearchViewModel
import org.koin.compose.viewmodel.koinViewModel

class SearchScreen : Tab {

    override val options: TabOptions
        @Composable
        get() = TabOptions(
            index = 1u,
            title = stringResource(id = R.string.navigation_item_search),
            icon = painterResource(id = R.drawable.ic_search),
        )

    @Composable
    override fun Content() {
        val viewModel = koinViewModel<SearchViewModel>()
        val navigator = LocalNavigator.current?.parent ?: return

        Content(
            input = viewModel.searchInput.collectAsState().value,
            meals = viewModel.meals.collectAsState().value,
            emptyItemData = viewModel.emptyItemData.collectAsState().value,
            onSearchInputChange = viewModel::onSearchInputChange,
            onSearchCloseClick = viewModel::onSearchCloseClick,
            onMealItemClick = {
                val screen = MealDetailsScreen(mealDetailsModel = it)
                navigator.push(screen)
            },
            onSaveIconClick = viewModel::onSaveIconClick,
        )
    }

    @Composable
    private fun Content(
        input: String = "",
        meals: List<MealDetailsModel> = emptyList(),
        emptyItemData: EmptyItemData? = null,
        onSearchInputChange: (String) -> Unit = { },
        onSearchCloseClick: () -> Unit = { },
        onMealItemClick: (MealDetailsModel) -> Unit = { },
        onSaveIconClick: (MealDetailsModel) -> Unit = { },
    ) {
        val focusRequester = remember(::FocusRequester)

        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = 24.dp,
                    start = 20.dp,
                    end = 20.dp
                )
                .clearFocusOnGesture()
        ) {
            Text(
                text = "Search",
                style = AppTheme.typography.H4
            )

            InputForm(
                input = input,
                hint = stringResource(id = R.string.search_hint),
                startIcon = painterResource(R.drawable.ic_search),
                endIcon = painterResource(R.drawable.ic_close)
                    .takeIf { input.isNotEmpty() },
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                onInputChange = onSearchInputChange,
                onEndIconClick = onSearchCloseClick,
            )

            if (emptyItemData != null) {
                EmptyItem(
                    data = emptyItemData,
                    modifier = Modifier.padding(top = 24.dp),
                )
            } else {
                MealDetailsList(
                    meals = meals,
                    contentPadding = PaddingValues(
                        start = 0.dp,
                        end = 0.dp,
                        top = 16.dp,
                        bottom = 84.dp,
                    ),
                    onItemClick = onMealItemClick,
                    onSaveIconClick = onSaveIconClick,
                )
            }
        }
    }

    @Preview(
        showBackground = true,
        heightDp = 380,
    )
    @Composable
    private fun InitialPreview() {
        FoodRecipesTheme {
            Content(
                emptyItemData = EmptyItemData(
                    iconId = R.drawable.ic_search,
                    titleId = R.string.search_get_started_title,
                    descriptionId = R.string.search_get_started_description,
                ),
            )
        }
    }

    @Preview(
        showBackground = true,
        heightDp = 380,
    )
    @Composable
    private fun EmptyResultPreview() {
        FoodRecipesTheme {
            Content(
                emptyItemData = EmptyItemData(
                    iconId = R.drawable.ic_file_error,
                    titleId = R.string.search_no_results_title,
                    descriptionId = R.string.search_no_results_description,
                ),
            )
        }
    }
}