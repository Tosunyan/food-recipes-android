package com.tosunyan.foodrecipes.ui.helpers

import com.tosunyan.foodrecipes.data.repositories.MealRepository
import com.tosunyan.foodrecipes.model.SaveableMeal

class MealSavingHelper(
    private val mealRepository: MealRepository,
) {

    suspend fun <T: SaveableMeal> toggleSavedState(meal: T, onUpdate: (Boolean) -> Unit) {
        onUpdate(!meal.isSaved)

        if (!meal.isSaved) {
            mealRepository.saveMeal(meal)
        } else {
            mealRepository.removeSavedMeal(meal)
        }

        updateStateIfNeeded(meal, onUpdate)
    }

    private suspend fun updateStateIfNeeded(
        meal: SaveableMeal,
        onUpdate: (Boolean) -> Unit
    ) {
        val actualSavedState = mealRepository.isMealSaved(meal)
        if (actualSavedState != !meal.isSaved) {
            onUpdate(actualSavedState)
        }
    }
}