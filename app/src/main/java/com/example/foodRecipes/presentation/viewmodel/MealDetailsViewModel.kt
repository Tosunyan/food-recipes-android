package com.example.foodRecipes.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.foodRecipes.datasource.repository.MealDetailsRepository
import com.example.foodRecipes.presentation.extension.convertToLiveData

class MealDetailsViewModel(
    private val repository: MealDetailsRepository = MealDetailsRepository()
) : ViewModel() {

    fun getMealDetails(id: String) = convertToLiveData {
        repository.getMealDetails(id)
    }
}