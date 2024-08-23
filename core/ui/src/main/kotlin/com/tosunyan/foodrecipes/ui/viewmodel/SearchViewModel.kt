package com.tosunyan.foodrecipes.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tosunyan.foodrecipes.common.utils.replace
import com.tosunyan.foodrecipes.data.repositories.MealRepository
import com.tosunyan.foodrecipes.data.repositories.SearchRepository
import com.tosunyan.foodrecipes.model.MealDetailsModel
import com.tosunyan.foodrecipes.network.api.ApiResponse
import com.tosunyan.foodrecipes.ui.helpers.MealSavingHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel(
    private val repository: SearchRepository = SearchRepository(),
    private val mealRepository: MealRepository = MealRepository(),
    private val mealSavingHelper: MealSavingHelper = MealSavingHelper(mealRepository)
) : ViewModel() {

    private val _meals = MutableStateFlow<List<MealDetailsModel>>(emptyList())
    val meals = _meals.asStateFlow()

    fun onSearchInputChange(text: String = "") {
        if (text.isBlank()) {
            _meals.value = emptyList()
            return
        }

        searchForMeals(text)
    }

    fun onSaveIconClick(meal: MealDetailsModel) {
        viewModelScope.launch {
            mealSavingHelper.toggleSavedState(meal) { isSaved ->
                _meals.update { meals ->
                    meals.replace(meal) { it.copy(isSaved = isSaved) }
                }
            }
        }
    }

    private fun searchForMeals(text: String) {
        viewModelScope.launch {
            val response = repository.searchMeals(text)

            if (response is ApiResponse.Success) {
                _meals.value = response.data
            }
        }
    }
}