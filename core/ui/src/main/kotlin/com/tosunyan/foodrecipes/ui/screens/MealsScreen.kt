package com.tosunyan.foodrecipes.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.inconceptlabs.designsystem.theme.AppTheme
import com.tosunyan.foodrecipes.model.CategoryModel
import com.tosunyan.foodrecipes.model.MealModel
import com.tosunyan.foodrecipes.model.RegionModel
import com.tosunyan.foodrecipes.ui.components.Toolbar
import com.tosunyan.foodrecipes.ui.components.meals.MealsList
import com.tosunyan.foodrecipes.ui.viewmodel.MealsViewModel

class MealsScreen(
    private val category: CategoryModel? = null,
    private val region: RegionModel? = null,
) : Screen {

    override val key: ScreenKey
        get() = this::class.simpleName!!

    @Suppress("unused", "PrivatePreviews")
    constructor(): this(null, null)

    @Composable
    override fun Content() {
        val viewModel = viewModel<MealsViewModel>()
        val navigator = LocalNavigator.currentOrThrow

        LaunchedEffect(category, region) {
            viewModel.onArgumentsReceive(category, region)
        }

        Content(
            isLoading = viewModel.isLoading.collectAsState().value,
            title = viewModel.title.collectAsState().value,
            meals = viewModel.meals.collectAsState().value,
            onBackClick = { navigator.pop() },
            onMealClick = {
                val screen = MealDetailsScreen(mealModel = it)
                navigator.push(screen)
            },
            onSaveIconClick = viewModel::onSaveIconClick
        )
    }

    @Composable
    private fun Content(
        isLoading: Boolean,
        title: String,
        meals: List<MealModel>,
        onBackClick: () -> Unit = {},
        onMealClick: (MealModel) -> Unit = {},
        onSaveIconClick: (MealModel) -> Unit = {},
    ) {
        Column(
            modifier = Modifier
                .background(AppTheme.colorScheme.BG1)
                .fillMaxSize()
        ) {
            Toolbar(
                title = title,
                onBackClick = onBackClick
            )

            MealsList(
                meals = meals,
                isLoading = isLoading,
                onItemClick = onMealClick,
                onSaveIconClick = onSaveIconClick,
            )
        }
    }

    @Preview(showBackground = true)
    @Composable
    private fun ContentPreview() {
        Content(
            isLoading = false,
            title = "Vegetables",
            meals = listOf(
                MealModel(id = "", name = "Potato"),
                MealModel(id = "", name = "Mushroom"),
                MealModel(id = "", name = "Tomato"),
                MealModel(id = "", name = "Cucumber"),
            )
        )
    }
}