package com.tosunyan.foodrecipes.main.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tosunyan.foodrecipes.data.repositories.HomeRepository
import com.tosunyan.foodrecipes.model.CategoryModel
import com.tosunyan.foodrecipes.model.MealDetailsModel
import com.tosunyan.foodrecipes.model.RegionModel
import com.tosunyan.foodrecipes.main.helpers.MealSavingHelper
import kotlinx.coroutines.async
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
        loadContent()
    }

    fun onSaveIconClick(meal: MealDetailsModel) {
        viewModelScope.launch {
            mealSavingHelper.toggleSavedState(meal) { isSaved ->
                randomMeal.update { it?.copy(isSaved = isSaved) }
            }
        }
    }

    // Loaded together so that sections don't appear one by one and push each other down
    private fun loadContent() {
        viewModelScope.launch {
            val randomMealDeferred = async { homeRepository.getRandomMeal() }
            val categoriesDeferred = async { homeRepository.getCategories() }
            val regionsDeferred = async { homeRepository.getRegions() }

            val loadedRandomMeal = randomMealDeferred.await()
            val loadedCategories = categoriesDeferred.await()
            val loadedRegions = regionsDeferred.await()

            randomMeal.value = loadedRandomMeal
            categories.value = loadedCategories
            regions.value = loadedRegions
        }
    }
}
