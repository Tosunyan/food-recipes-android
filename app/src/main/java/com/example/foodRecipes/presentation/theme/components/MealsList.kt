package com.example.foodRecipes.presentation.theme.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.foodRecipes.domain.model.MealDetailsModel
import com.example.foodRecipes.domain.model.MealModel
import com.example.foodRecipes.presentation.theme.components.listitem.MealItem

private val defaultPadding = PaddingValues(
    vertical = 24.dp,
    horizontal = 20.dp,
)

@Composable
fun MealDetailsList(
    meals: List<MealDetailsModel>,
    contentPadding: PaddingValues = defaultPadding,
    onItemClick: (MealDetailsModel) -> Unit = { }
) {
    LazyColumn(
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(
            key = MealDetailsModel::id,
            items = meals
        ) {
            DailySpecialItem(
                item = it,
                modifier = Modifier.fillMaxWidth(),
                onClick = onItemClick
            )
        }
    }
}

@Composable
fun MealsList(
    meals: List<MealModel>,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    contentPadding: PaddingValues = defaultPadding,
    onItemClick: (MealModel) -> Unit = { }
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
            )
        }
    }
}