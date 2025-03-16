package com.tosunyan.foodrecipes.ui.theme

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import com.inconceptlabs.designsystem.theme.AppTheme
import com.tosunyan.foodrecipes.ui.theme.colors.LightColorScheme
import com.tosunyan.foodrecipes.ui.theme.indication.ScaleIndicationNodeFactory

@OptIn(ExperimentalSharedTransitionApi::class)
val LocalSharedTransitionScope = staticCompositionLocalOf<SharedTransitionScope> {
    error("Not yet initialized!")
}

val LocalAnimatedContentScope = staticCompositionLocalOf<AnimatedContentScope> {
    error("Not yet initialized!")
}

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