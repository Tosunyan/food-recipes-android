package com.tosunyan.foodrecipes.ui.viewmodel

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tosunyan.foodrecipes.data.repositories.HomeRepository
import com.tosunyan.foodrecipes.data.repositories.MealRepository
import com.tosunyan.foodrecipes.model.CategoryModel
import com.tosunyan.foodrecipes.model.MealDetailsModel
import com.tosunyan.foodrecipes.model.RegionModel
import com.tosunyan.foodrecipes.network.api.ApiResponse
import com.tosunyan.foodrecipes.ui.R
import com.tosunyan.foodrecipes.ui.components.notification.NotificationData
import com.tosunyan.foodrecipes.ui.helpers.MealSavingHelper
import com.tosunyan.foodrecipes.ui.helpers.NotificationManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class HomeViewModel(
    private val homeRepository: HomeRepository = HomeRepository(),
    private val mealRepository: MealRepository = MealRepository(),
    private val mealSavingHelper: MealSavingHelper = MealSavingHelper(mealRepository)
) : ViewModel() {

    val randomMeal = MutableStateFlow<MealDetailsModel?>(null)
    val categories = MutableStateFlow<List<CategoryModel>>(emptyList())
    val regions = MutableStateFlow<List<RegionModel>>(emptyList())

    init {
        makeApiCalls()
    }

    fun onSaveIconClick(meal: MealDetailsModel) {
        viewModelScope.launch {
            mealSavingHelper.onSaveToggleClick(meal) { isSaved ->
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
            when (val response = homeRepository.getRandomMeal()) {
                is ApiResponse.Success -> randomMeal.value = response.data
                is ApiResponse.Failure -> showErrorNotification(R.string.error_fetching_daily_special)
            }
        }
    }

    private fun getCategories() {
        viewModelScope.launch {
            when (val response = homeRepository.getCategories()) {
                is ApiResponse.Success -> categories.value = response.data
                is ApiResponse.Failure -> showErrorNotification(R.string.error_fetching_categories)
            }
        }
    }

    private fun getRegions() {
        viewModelScope.launch {
            when (val response = homeRepository.getRegions()) {
                is ApiResponse.Success -> regions.value = response.data
                is ApiResponse.Failure -> showErrorNotification(R.string.error_fetching_regions)
            }
        }
    }

    private fun showErrorNotification(@StringRes messageId: Int) {
        viewModelScope.launch {
            val notification = NotificationData(titleResId = messageId)
            NotificationManager.showNotification(notification)
        }
    }
}