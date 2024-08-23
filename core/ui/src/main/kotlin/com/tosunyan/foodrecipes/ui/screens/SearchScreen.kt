package com.tosunyan.foodrecipes.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.inconceptlabs.designsystem.components.core.Text
import com.inconceptlabs.designsystem.components.input.InputForm
import com.inconceptlabs.designsystem.theme.AppTheme
import com.inconceptlabs.designsystem.utils.clearFocusOnGesture
import com.tosunyan.foodrecipes.model.MealDetailsModel
import com.tosunyan.foodrecipes.ui.R
import com.tosunyan.foodrecipes.ui.theme.components.meals.MealDetailsList
import com.tosunyan.foodrecipes.ui.viewmodel.SearchViewModel

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
        val viewModel = viewModel<SearchViewModel>()
        val navigator = LocalNavigator.current?.parent ?: return

        Content(
            meals = viewModel.meals.collectAsState().value,
            onSearchInputChange = viewModel::onSearchInputChange,
            onMealItemClick = {
                val screen = MealDetailsScreen(mealDetailsModel = it)
                navigator.push(screen)
            },
            onSaveIconClick = viewModel::onSaveIconClick,
        )
    }

    @Composable
    private fun Content(
        meals: List<MealDetailsModel>,
        onSearchInputChange: (String) -> Unit,
        onMealItemClick: (MealDetailsModel) -> Unit,
        onSaveIconClick: (MealDetailsModel) -> Unit,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .background(Color.White)
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
                hint = stringResource(id = R.string.search_hint),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                onInputChange = onSearchInputChange
            )

            MealDetailsList(
                meals = meals,
                contentPadding = PaddingValues(horizontal = 0.dp, vertical = 24.dp),
                onItemClick = onMealItemClick,
                onSaveIconClick = onSaveIconClick,
            )
        }
    }
}