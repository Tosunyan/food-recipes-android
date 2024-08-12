package com.example.foodRecipes.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.foodRecipes.domain.model.CategoryModel
import com.example.foodRecipes.domain.model.MealModel
import com.example.foodRecipes.domain.model.RegionModel
import com.example.foodRecipes.presentation.theme.components.MealsList
import com.example.foodRecipes.presentation.theme.components.Toolbar
import com.example.foodRecipes.presentation.viewmodel.MealsViewModel
import kotlinx.parcelize.Parcelize

@Parcelize
class MealsScreen(
    private val category: CategoryModel? = null,
    private val region: RegionModel? = null,
) : ParcelableScreen {

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
            }
        )
    }

    @Composable
    private fun Content(
        isLoading: Boolean,
        title: String,
        meals: List<MealModel>,
        onBackClick: () -> Unit = {},
        onMealClick: (MealModel) -> Unit = {},
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
        ) {
            Toolbar(
                title = title,
                onBackClick = onBackClick
            )

            MealsList(
                meals = meals,
                isLoading = isLoading,
                onItemClick = onMealClick
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