package com.tosunyan.foodrecipes.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tosunyan.foodrecipes.data.repositories.HomeRepository
import com.tosunyan.foodrecipes.model.CategoryModel
import com.tosunyan.foodrecipes.model.MealDetailsModel
import com.tosunyan.foodrecipes.model.RegionModel
import com.tosunyan.foodrecipes.ui.helpers.MealSavingHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val homeRepository: HomeRepository,
    private val mealSavingHelper: MealSavingHelper,
) : ViewModel() {

    val randomMeal = MutableStateFlow<MealDetailsModel?>(null)
    val categories = MutableStateFlow<List<CategoryModel>>(emptyList())
    val regions = MutableStateFlow<List<RegionModel>>(emptyList())

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
            randomMeal.update { homeRepository.getRandomMeal() }
        }
    }

    private fun getCategories() {
        viewModelScope.launch {
            categories.update { homeRepository.getCategories() }
        }
    }

    private fun getRegions() {
        viewModelScope.launch {
            regions.update { homeRepository.getRegions() }
        }
    }
}