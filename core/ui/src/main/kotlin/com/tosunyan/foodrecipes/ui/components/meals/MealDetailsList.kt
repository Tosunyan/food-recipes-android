package com.tosunyan.foodrecipes.ui.components.meals

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.inconceptlabs.designsystem.components.core.Divider
import com.tosunyan.foodrecipes.model.MealDetailsModel

@Composable
fun MealDetailsList(
    meals: List<MealDetailsModel>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = defaultPadding,
    onItemClick: (MealDetailsModel) -> Unit = { },
    onSaveIconClick: (MealDetailsModel) -> Unit = { },
) {
    val lazyListState = rememberLazyListState()
    val focusManager = LocalFocusManager.current

    val spacing = 12.dp

    LaunchedEffect(Unit) {
        lazyListState.interactionSource.interactions.collect {
            focusManager.clearFocus()
        }
    }

    LazyColumn(
        state = lazyListState,
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(spacing),
        modifier = modifier.fillMaxSize()
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

            Divider(
                modifier = Modifier.padding(top = spacing)
            )
        }
    }
}