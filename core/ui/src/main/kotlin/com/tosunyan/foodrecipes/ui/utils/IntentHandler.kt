package com.tosunyan.foodrecipes.ui.utils

import android.content.Intent
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.core.util.Consumer
import com.tosunyan.foodrecipes.model.MealModel
import com.tosunyan.foodrecipes.ui.screens.mealdetails.MealDetailsScreen

@Composable
fun IntentHandler(backStack: BackStack) {
    val activity = LocalActivity.current as? ComponentActivity ?: return
    val intent = activity.intent

    val listener = IntentConsumer(backStack)

    LaunchedEffect(intent) {
        listener.accept(intent)
    }

    DisposableEffect(intent) {
        activity.addOnNewIntentListener(listener)
        onDispose { activity.removeOnNewIntentListener(listener) }
    }
}

class IntentConsumer(private val backStack: BackStack) : Consumer<Intent> {

    override fun accept(value: Intent) {
        val intentData = value.data
        if (value.action != Intent.ACTION_VIEW || intentData == null) return

        runCatching {
            when {
                "meal" in intentData.pathSegments -> {
                    navigateToMealDetails(backStack, intentData)
                }
                else -> {
                    error("Invalid URL")
                }
            }
        }.onFailure {
            if (it is IllegalStateException) {
//                showToast(R.string.error_invalid_url, true)
            }
        }
    }
}

private fun navigateToMealDetails(backStack: BackStack, intentData: Uri) {
    val mealId = intentData.lastPathSegment ?: error("Invalid URL")
    if (mealId.toIntOrNull() == null) error("Invalid URL")

    val mealModel = MealModel(id = mealId)
    val screen = MealDetailsScreen(mealModel = mealModel)

    val isDetailsOpened = backStack.lastOrNull == screen
    if (!isDetailsOpened) {
        backStack.push(screen)
    }
}