package com.tosunyan.foodrecipes.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.inconceptlabs.designsystem.components.core.Text
import com.inconceptlabs.designsystem.theme.AppTheme
import com.tosunyan.foodrecipes.model.MealDetailsModel
import com.tosunyan.foodrecipes.ui.R
import com.tosunyan.foodrecipes.ui.theme.components.MealDetailsList
import com.tosunyan.foodrecipes.ui.viewmodel.SavedMealsViewModel

class SavedMealsScreen : Tab {

    override val options: TabOptions
        @Composable
        get() = TabOptions(
            index = 2u,
            title = stringResource(id = R.string.navigation_item_saved_meals),
        )

    @Composable
    override fun Content() {
        val viewModel = viewModel<SavedMealsViewModel>()
        val navigator = LocalNavigator.current?.parent ?: return

        Content(
            meals = viewModel.savedMeals.collectAsState().value,
            onMealClick = {
                val screen = MealDetailsScreen(mealDetailsModel = it)
                navigator.push(screen)
            },
            onSaveIconClick = viewModel::onSaveIconClick
        )
    }

    @Composable
    private fun Content(
        meals: List<MealDetailsModel>,
        onMealClick: (MealDetailsModel) -> Unit,
        onSaveIconClick: (MealDetailsModel) -> Unit,
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
                .padding(
                    start = 20.dp,
                    end = 20.dp,
                    top = 24.dp
                )
        ) {
            Text(
                text = stringResource(id = R.string.navigation_item_saved_meals),
                style = AppTheme.typography.H4,
            )

            if (meals.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = stringResource(id = R.string.saved_meals_empty_list_title),
                        style = AppTheme.typography.S1,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .align(Alignment.Center)
                    )
                }
            } else {
                MealDetailsList(
                    meals = meals,
                    contentPadding = PaddingValues(vertical = 24.dp),
                    onItemClick = onMealClick,
                    onSaveIconClick = onSaveIconClick,
                )
            }
        }
    }
}