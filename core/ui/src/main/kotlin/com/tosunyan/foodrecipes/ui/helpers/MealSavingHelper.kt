package com.tosunyan.foodrecipes.ui.helpers

import com.inconceptlabs.designsystem.theme.AppTheme
import com.tosunyan.foodrecipes.data.repositories.MealRepository
import com.tosunyan.foodrecipes.model.SaveableMeal
import com.tosunyan.foodrecipes.ui.R
import com.tosunyan.foodrecipes.ui.components.notification.NotificationData
import kotlinx.coroutines.delay
import kotlinx.coroutines.selects.select
import kotlin.time.Duration.Companion.seconds

internal class MealSavingHelper(
    private val mealRepository: MealRepository,
    private val notificationManager: NotificationManager = NotificationManager,
) {

    suspend fun onSaveToggleClick(
        meal: SaveableMeal,
        onUpdate: (Boolean) -> Unit,
    ) {
        if (meal.isSaved) {
            onUpdate(false)
            showMealRemovedNotification(meal, onUpdate)
        } else {
            toggleSavedState(meal, onUpdate)
            showMealSavedNotification()
        }
    }

    private suspend fun toggleSavedState(meal: SaveableMeal, onUpdate: (Boolean) -> Unit) {
        onUpdate(!meal.isSaved)

        val isSaved = mealRepository.toggleSavedState(meal)

        updateStateIfNeeded(
            meal = meal,
            actualSavedState = isSaved,
            onUpdate = onUpdate
        )
    }

    private suspend fun showMealSavedNotification() {
        val data = NotificationData(
            titleResId = R.string.message_recipe_saved,
            startIconResId = R.drawable.ic_check_fill,
            iconTint = { AppTheme.colorScheme.success.main },
        )
        notificationManager.showNotification(data, duration = 2.seconds)
    }

    private suspend fun showMealRemovedNotification(
        meal: SaveableMeal,
        onUpdate: (Boolean) -> Unit,
    ) {
        var shouldDelete = true
        val data = NotificationData(
            titleResId = R.string.message_recipe_removed,
            startIconResId = R.drawable.ic_warning_fill,
            buttonTextResId = R.string.undo,
            iconTint = { AppTheme.colorScheme.error.main },
            onClick = {
                shouldDelete = false
            }
        )

        notificationManager.showNotification(data)

        if (shouldDelete) {
            val isRemoved = mealRepository.removeSavedMeal(meal)
            if (!isRemoved) {
                onUpdate(meal.isSaved)
            }
        }
    }

    private fun updateStateIfNeeded(
        meal: SaveableMeal,
        actualSavedState: Boolean,
        onUpdate: (Boolean) -> Unit,
    ) {
        if (actualSavedState != !meal.isSaved) {
            onUpdate(actualSavedState)
        }
    }
}