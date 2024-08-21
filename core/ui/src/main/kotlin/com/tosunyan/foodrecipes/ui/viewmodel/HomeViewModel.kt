package com.tosunyan.foodrecipes.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tosunyan.foodrecipes.data.repositories.HomeRepository
import com.tosunyan.foodrecipes.model.CategoryModel
import com.tosunyan.foodrecipes.model.MealDetailsModel
import com.tosunyan.foodrecipes.model.RegionModel
import com.tosunyan.foodrecipes.network.api.ApiResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class HomeViewModel(
    private val homeRepository: HomeRepository = HomeRepository()
) : ViewModel() {

    val randomMeal = MutableStateFlow<MealDetailsModel?>(null)
    val categories = MutableStateFlow<List<CategoryModel>>(emptyList())
    val regions = MutableStateFlow<List<RegionModel>>(emptyList())

    val errorMessage = MutableStateFlow<String?>(null)

    init {
        makeApiCalls()
    }

    private fun makeApiCalls() {
        getRandomMeal()
        getCategories()
        getRegions()
    }

    private fun getRandomMeal() {
        viewModelScope.launch {
            when (val response = homeRepository.getRandomMeal()) {
                is ApiResponse.Success -> randomMeal.value = response.data
                is ApiResponse.Failure -> showErrorMessage("Failed to fetch today's meal")
            }
        }
    }

    private fun getCategories() {
        viewModelScope.launch {
            when (val response = homeRepository.getCategories()) {
                is ApiResponse.Success -> categories.value = response.data
                is ApiResponse.Failure -> showErrorMessage("Failed to fetch categories")
            }
        }
    }

    private fun getRegions() {
        viewModelScope.launch {
            when (val response = homeRepository.getRegions()) {
                is ApiResponse.Success -> regions.value = response.data
                is ApiResponse.Failure -> showErrorMessage("Failed to fetch regions")
            }
        }
    }

    private fun showErrorMessage(message: String) {
        viewModelScope.launch {
            errorMessage.value = message
            delay(1.seconds)
            errorMessage.value = null
        }
    }
}