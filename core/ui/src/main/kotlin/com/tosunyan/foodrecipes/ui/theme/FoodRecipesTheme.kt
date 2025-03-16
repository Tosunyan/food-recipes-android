package com.tosunyan.foodrecipes.ui.theme

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import com.inconceptlabs.designsystem.theme.AppTheme
import com.tosunyan.foodrecipes.ui.theme.colors.LightColorScheme
import com.tosunyan.foodrecipes.ui.theme.indication.ScaleIndicationNodeFactory

@Composable
fun FoodRecipesTheme(
    content: @Composable () -> Unit
) {
    AppTheme(
        colorScheme = LightColorScheme,
        indication = ScaleIndicationNodeFactory,
        content = content,
    )
}

fun ComponentActivity.setThemedContent(
    content: @Composable () -> Unit
) {
    setContent {
        FoodRecipesTheme(content)
    }
}