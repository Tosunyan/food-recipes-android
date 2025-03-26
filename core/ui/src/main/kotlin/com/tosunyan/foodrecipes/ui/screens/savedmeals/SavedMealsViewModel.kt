package com.tosunyan.foodrecipes.ui.screens.savedmeals

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tosunyan.foodrecipes.common.coroutines.WhileSubscribedOrRetained
import com.tosunyan.foodrecipes.data.repositories.MealRepository
import com.tosunyan.foodrecipes.model.MealDetailsModel
import com.tosunyan.foodrecipes.ui.helpers.MealSavingHelper
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SavedMealsViewModel(
    repository: MealRepository,
    private val mealSavingHelper: MealSavingHelper,
) : ViewModel() {

    val savedMeals: StateFlow<List<MealDetailsModel>> =
        repository.getSavedMealsFlow()
            .stateIn(
                scope = viewModelScope,
                started = WhileSubscribedOrRetained,
                initialValue = emptyList()
            )

    fun onSaveIconClick(meal: MealDetailsModel) {
        viewModelScope.launch {
            mealSavingHelper.toggleSavedState(meal) {}
        }
    }
}