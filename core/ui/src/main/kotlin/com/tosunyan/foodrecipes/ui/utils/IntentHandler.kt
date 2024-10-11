package com.tosunyan.foodrecipes.ui.utils

import android.content.Intent
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.core.util.Consumer
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import com.tosunyan.foodrecipes.model.MealModel
import com.tosunyan.foodrecipes.ui.R
import com.tosunyan.foodrecipes.ui.mealdetails.MealDetailsScreen

@Composable
fun ComponentActivity.IntentHandler() {
    val navigator = LocalNavigator.current ?: return

    LaunchedEffect(intent) {
        onNewIntent(navigator)
    }

    DisposableEffect(intent) {
        val listener = Consumer<Intent> {
            intent = it

            onNewIntent(navigator)
        }

        addOnNewIntentListener(listener)
        onDispose { removeOnNewIntentListener(listener) }
    }
}

private fun ComponentActivity.onNewIntent(navigator: Navigator) {
    val intentData = intent.data
    if (intent.action != Intent.ACTION_VIEW || intentData == null) return

    runCatching {
        when {
            "meal" in intentData.pathSegments -> {
                navigateToMealDetails(navigator, intentData)
            }
            else -> {
                error("Invalid URL")
            }
        }
    }.onFailure {
        if (it is IllegalStateException) {
            showToast(R.string.error_invalid_url, true)
        }
    }
}

private fun navigateToMealDetails(
    navigator: Navigator,
    intentData: Uri,
) {
    val mealId = intentData.lastPathSegment ?: error("Invalid URL")
    if (mealId.toIntOrNull() == null) error("Invalid URL")

    val mealModel = MealModel(id = mealId)
    val screen = MealDetailsScreen(mealModel = mealModel)

    val isDetailsOpened = navigator.lastItem.key == mealId
    if (!isDetailsOpened) {
        navigator.push(screen)
    }
}