package com.example.foodRecipes.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.foodRecipes.R
import com.example.foodRecipes.domain.model.MealModel
import com.example.foodRecipes.presentation.theme.components.MealsList
import com.inconceptlabs.designsystem.components.core.Text
import com.inconceptlabs.designsystem.components.input.InputForm
import com.inconceptlabs.designsystem.theme.AppTheme
import com.inconceptlabs.designsystem.utils.clearFocusOnGesture

@Composable
fun SearchScreen(
    meals: List<MealModel>,
    onSearchInputChange: (String) -> Unit,
    onMealItemClick: (MealModel) -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = 24.dp,
                start = 16.dp,
                end = 16.dp
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

        MealsList(
            meals = meals,
            contentPadding = PaddingValues(horizontal = 0.dp, vertical = 24.dp),
            onItemClick = onMealItemClick
        )
    }
}