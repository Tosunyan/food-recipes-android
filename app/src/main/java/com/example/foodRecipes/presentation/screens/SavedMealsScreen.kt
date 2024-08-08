package com.example.foodRecipes.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.foodRecipes.R
import com.example.foodRecipes.domain.model.MealDetailsModel
import com.example.foodRecipes.presentation.theme.components.MealDetailsList
import com.inconceptlabs.designsystem.components.core.Text
import com.inconceptlabs.designsystem.theme.AppTheme

@Composable
fun SavedMealsScreen(
    meals: List<MealDetailsModel>,
    onMealClick: (MealDetailsModel) -> Unit,
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
            text = stringResource(id = R.string.saved_meals_title),
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
            )
        }
    }
}