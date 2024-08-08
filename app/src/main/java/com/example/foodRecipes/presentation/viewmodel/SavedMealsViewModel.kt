package com.example.foodRecipes.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodRecipes.datasource.repository.MealRepository
import com.example.foodRecipes.domain.model.MealDetailsModel
import com.example.foodRecipes.util.WhileSubscribedOrRetained
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

@Suppress("CanBeParameter")
class SavedMealsViewModel(
    private val repository: MealRepository = MealRepository(),
) : ViewModel() {

    val savedMeals: StateFlow<List<MealDetailsModel>> =
        repository.getSavedMealsFlow()
            .stateIn(
                scope = viewModelScope,
                started = WhileSubscribedOrRetained,
                initialValue = emptyList()
            )
}