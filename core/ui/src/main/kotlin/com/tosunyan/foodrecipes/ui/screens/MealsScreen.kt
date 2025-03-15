package com.tosunyan.foodrecipes.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.AsyncImage
import com.inconceptlabs.designsystem.theme.AppTheme
import com.tosunyan.foodrecipes.model.CategoryModel
import com.tosunyan.foodrecipes.model.MealModel
import com.tosunyan.foodrecipes.model.RegionModel
import com.tosunyan.foodrecipes.ui.components.Toolbar
import com.tosunyan.foodrecipes.ui.components.meals.MealsList
import com.tosunyan.foodrecipes.ui.mealdetails.MealDetailsScreen
import com.tosunyan.foodrecipes.ui.theme.FoodRecipesTheme
import com.tosunyan.foodrecipes.ui.viewmodel.MealsViewModel
import eu.wewox.textflow.TextFlow
import eu.wewox.textflow.TextFlowObstacleAlignment
import org.koin.compose.viewmodel.koinViewModel

class MealsScreen(
    private val category: CategoryModel? = null,
    private val region: RegionModel? = null,
) : Screen {

    override val key: ScreenKey
        get() = this::class.simpleName!!

    @Suppress("unused", "PrivatePreviews")
    constructor() : this(null, null)

    @Composable
    override fun Content() {
        val viewModel = koinViewModel<MealsViewModel>()
        val navigator = LocalNavigator.currentOrThrow

        LaunchedEffect(category, region) {
            viewModel.onArgumentsReceive(category, region)
        }

        Content(
            state = viewModel.screenState.collectAsState().value,
            onBackClick = { navigator.pop() },
            onMealClick = {
                val screen = MealDetailsScreen(mealModel = it)
                navigator.push(screen)
            },
            onSaveIconClick = viewModel::onSaveIconClick,
        )
    }

    @Composable
    private fun Content(
        state: MealsViewModel.ScreenState,
        onBackClick: () -> Unit = {},
        onMealClick: (MealModel) -> Unit = {},
        onSaveIconClick: (MealModel) -> Unit = {},
    ) {
        Column(
            modifier = Modifier
                .background(AppTheme.colorScheme.BG1)
                .fillMaxSize()
                .navigationBarsPadding()
        ) {
            Toolbar(
                title = state.title,
                onBackClick = onBackClick
            )

            MealsList(
                meals = state.meals,
                isLoading = state.isLoading,
                onItemClick = onMealClick,
                onSaveIconClick = onSaveIconClick,
                leadingContent = { infoItem(state = state) }
            )
        }
    }

    private fun LazyGridScope.infoItem(state: MealsViewModel.ScreenState) {
        if (state.description.isNullOrBlank()) return

        item(
            key = "CategoryInfoItem",
            span = { GridItemSpan(2) }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .background(
                        color = AppTheme.colorScheme.BG3,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(12.dp)
            ) {
                TextFlow(
                    text = state.description,
                    style = AppTheme.typography.P4,
                    modifier = Modifier.weight(1f),
                    obstacleAlignment = TextFlowObstacleAlignment.TopStart,
                    obstacleContent = {
                        AsyncImage(
                            model = state.thumbnailUrl,
                            contentDescription = null,
                            modifier = Modifier
                                .padding(end = 16.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .height(80.dp),
                        )
                    }
                )
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    private fun ContentPreview() {
        FoodRecipesTheme {
            Content(
                state = MealsViewModel.ScreenState(
                    isLoading = false,
                    title = "Vegetables",
                    meals = listOf(
                        MealModel(id = "1", name = "Potato"),
                        MealModel(id = "2", name = "Mushroom"),
                        MealModel(id = "3", name = "Tomato"),
                        MealModel(id = "4", name = "Cucumber"),
                    )
                ),
            )
        }
    }
}