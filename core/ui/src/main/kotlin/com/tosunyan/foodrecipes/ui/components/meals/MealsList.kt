package com.tosunyan.foodrecipes.ui.components.meals

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tosunyan.foodrecipes.model.MealModel

val defaultPadding = PaddingValues(
    vertical = 24.dp,
    horizontal = 20.dp,
)

@Composable
fun MealsList(
    meals: List<MealModel>,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    contentPadding: PaddingValues = defaultPadding,
    onItemClick: (MealModel) -> Unit = { },
    onSaveIconClick: (MealModel) -> Unit = { },
) {
    LazyVerticalGrid(
        contentPadding = contentPadding,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        columns = GridCells.Fixed(2),
        modifier = modifier
            .fillMaxSize(),
    ) {
        items(
            key = MealModel::id,
            items = meals,
        ) { mealModel ->
            MealItem(
                item = mealModel,
                isLoading = isLoading,
                onClick = onItemClick,
                onSaveIconClick = onSaveIconClick,
            )
        }
    }
}