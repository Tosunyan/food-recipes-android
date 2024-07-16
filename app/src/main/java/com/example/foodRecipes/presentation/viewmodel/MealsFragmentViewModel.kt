package com.example.foodRecipes.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.foodRecipes.datasource.repository.MealRepository
import com.example.foodRecipes.presentation.extension.convertToLiveData

class MealsFragmentViewModel(
    private val mealRepository: MealRepository = MealRepository()
): ViewModel() {

    fun filterMealsByCategory(category: String) = convertToLiveData {
        mealRepository.filterMealsByCategory(category)
    }

    fun filterMealsByArea(area: String) = convertToLiveData {
        mealRepository.filterMealsByArea(area)
    }
}