package com.example.foodRecipes.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodRecipes.datasource.remote.api.ApiResponse
import com.example.foodRecipes.datasource.repository.SearchRepository
import com.example.foodRecipes.domain.model.MealModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchViewModel(
    private val repository: SearchRepository = SearchRepository()
) : ViewModel() {

    private val _meals = MutableStateFlow<List<MealModel>>(emptyList())
    val meals = _meals.asStateFlow()

    fun onSearchInputChange(text: String = "") {
        if (text.trim().isBlank()) return

        searchForMeals(text)
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