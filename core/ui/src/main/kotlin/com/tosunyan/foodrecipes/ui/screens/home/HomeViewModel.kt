package com.tosunyan.foodrecipes.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tosunyan.foodrecipes.data.repositories.HomeRepository
import com.tosunyan.foodrecipes.model.CategoryModel
import com.tosunyan.foodrecipes.model.MealDetailsModel
import com.tosunyan.foodrecipes.model.RegionModel
import com.tosunyan.foodrecipes.ui.helpers.MealSavingHelper
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class HomeViewModel(
    private val homeRepository: HomeRepository,
    private val mealSavingHelper: MealSavingHelper,
) : ViewModel() {

    val randomMeal = MutableStateFlow<MealDetailsModel?>(null)
    val categories = MutableStateFlow<List<CategoryModel>>(emptyList())
    val regions = MutableStateFlow<List<RegionModel>>(emptyList())

    val errorMessage = MutableStateFlow<String?>(null)

    init {
        makeApiCalls()
    }

    fun onSaveIconClick(meal: MealDetailsModel) {
        viewModelScope.launch {
            mealSavingHelper.toggleSavedState(meal) { isSaved ->
                randomMeal.update { it?.copy(isSaved = isSaved) }
            }
        }
    }

    private fun makeApiCalls() {
        getRandomMeal()
        getCategories()
        getRegions()
    }

    private fun getRandomMeal() {
        viewModelScope.launch {
            homeRepository.getRandomMeal()
                .onSuccess { randomMeal.value = it }
                .onFailure { showErrorMessage("Failed to fetch today's meal") }
        }
    }

    private fun getCategories() {
        viewModelScope.launch {
            homeRepository.getCategories()
                .onSuccess { categories.value = it }
                .onFailure { showErrorMessage("Failed to fetch categories") }
        }
    }

    private fun getRegions() {
        viewModelScope.launch {
            homeRepository.getRegions()
                .onSuccess { regions.value = it }
                .onFailure { showErrorMessage("Failed to fetch regions") }
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