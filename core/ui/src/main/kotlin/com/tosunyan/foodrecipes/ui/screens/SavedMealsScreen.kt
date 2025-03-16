package com.tosunyan.foodrecipes.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.inconceptlabs.designsystem.components.core.Text
import com.inconceptlabs.designsystem.components.emptyitem.EmptyItem
import com.inconceptlabs.designsystem.theme.AppTheme
import com.tosunyan.foodrecipes.model.MealDetailsModel
import com.tosunyan.foodrecipes.ui.R
import com.tosunyan.foodrecipes.ui.components.meals.MealDetailsList
import com.tosunyan.foodrecipes.ui.mealdetails.MealDetailsScreen
import com.tosunyan.foodrecipes.ui.theme.FoodRecipesTheme
import com.tosunyan.foodrecipes.ui.viewmodel.SavedMealsViewModel
import org.koin.compose.viewmodel.koinViewModel

class SavedMealsScreen : Tab {

    override val options: TabOptions
        @Composable
        get() = TabOptions(
            index = 2u,
            title = stringResource(id = R.string.navigation_item_saved_meals),
        )

    @Composable
    override fun Content() {
        val viewModel = koinViewModel<SavedMealsViewModel>()
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
        meals: List<MealDetailsModel> = emptyList(),
        onMealClick: (MealDetailsModel) -> Unit = { },
        onSaveIconClick: (MealDetailsModel) -> Unit = { },
    ) {
        Column(
            modifier = Modifier
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
                EmptyItem(
                    icon = painterResource(R.drawable.ic_bookmark),
                    title = stringResource(R.string.saved_meals_empty_list_title),
                    description = stringResource(R.string.saved_meals_empty_list_description),
                    modifier = Modifier.padding(top = 24.dp),
                )
            } else {
                MealDetailsList(
                    meals = meals,
                    modifier = Modifier.padding(top = 8.dp),
                    contentPadding = PaddingValues(
                        top = 16.dp,
                        bottom = 84.dp
                    ),
                    onItemClick = onMealClick,
                    onSaveIconClick = onSaveIconClick,
                )
            }
        }
    }

    @Preview(
        showBackground = true,
        heightDp = 340,
    )
    @Composable
    private fun EmptyResultPreview() {
        FoodRecipesTheme {
            Content(
                meals = emptyList()
            )
        }
    }

    @Preview(
        showBackground = true,
        heightDp = 480,
    )
    @Composable
    private fun DefaultScreenPreview() {
        FoodRecipesTheme {
            Content(
                meals = listOf(
                    MealDetailsModel(
                        id = "0",
                        isSaved = true,
                        name = "Pepperoni",
                        category = "Pizza",
                        region = "Italy",
                    ),
                    MealDetailsModel(
                        id = "1",
                        isSaved = true,
                        name = "Spaghetti",
                        category = "Pasta",
                        region = "Italy",
                    ),
                )
            )
        }
    }
}