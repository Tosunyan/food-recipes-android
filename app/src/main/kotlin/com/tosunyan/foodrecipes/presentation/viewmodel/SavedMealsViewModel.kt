package com.tosunyan.foodrecipes.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tosunyan.foodrecipes.datasource.repository.MealRepository
import com.tosunyan.foodrecipes.domain.model.MealDetailsModel
import com.tosunyan.foodrecipes.util.WhileSubscribedOrRetained
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