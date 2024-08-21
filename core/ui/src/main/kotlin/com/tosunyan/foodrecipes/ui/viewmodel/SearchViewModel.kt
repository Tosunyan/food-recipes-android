package com.tosunyan.foodrecipes.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tosunyan.foodrecipes.data.repositories.SearchRepository
import com.tosunyan.foodrecipes.model.MealDetailsModel
import com.tosunyan.foodrecipes.network.api.ApiResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchViewModel(
    private val repository: SearchRepository = SearchRepository()
) : ViewModel() {

    private val _meals = MutableStateFlow<List<MealDetailsModel>>(emptyList())
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