package com.tosunyan.foodrecipes.ui.theme.components.meals

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tosunyan.foodrecipes.model.MealDetailsModel

@Composable
fun MealDetailsList(
    meals: List<MealDetailsModel>,
    contentPadding: PaddingValues = defaultPadding,
    onItemClick: (MealDetailsModel) -> Unit = { },
    onSaveIconClick: (MealDetailsModel) -> Unit = { },
) {
    LazyColumn(
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(
            key = MealDetailsModel::id,
            items = meals,
        ) {
            MealDetailsItem(
                item = it,
                modifier = Modifier
                    .animateItem()
                    .fillMaxWidth(),
                onClick = onItemClick,
                onSaveIconClick = onSaveIconClick
            )
        }
    }
}