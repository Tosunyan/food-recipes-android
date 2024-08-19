package com.tosunyan.foodrecipes.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tosunyan.foodrecipes.datasource.remote.api.ApiResponse
import com.tosunyan.foodrecipes.datasource.repository.HomeRepository
import com.tosunyan.foodrecipes.domain.model.CategoryModel
import com.tosunyan.foodrecipes.domain.model.MealDetailsModel
import com.tosunyan.foodrecipes.domain.model.RegionModel
import com.tosunyan.foodrecipes.domain.usecase.GetCategories
import com.tosunyan.foodrecipes.domain.usecase.GetRegions
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class HomeViewModel(
    private val getCategories: GetCategories = GetCategories,
    private val getRegions: GetRegions = GetRegions,
    private val homeRepository: HomeRepository = HomeRepository()
) : ViewModel() {

    private val randomMealResponse = MutableSharedFlow<ApiResponse<MealDetailsModel?>>()
    private val categoriesResponse = MutableSharedFlow<ApiResponse<List<CategoryModel>>>()
    private val regionsResponse = MutableSharedFlow<ApiResponse<List<RegionModel>>>()

    val randomMeal = MutableStateFlow<MealDetailsModel?>(null)
    val categories = MutableStateFlow<List<CategoryModel>>(emptyList())
    val regions = MutableStateFlow<List<RegionModel>>(emptyList())

    val errorMessage = MutableStateFlow<String?>(null)

    init {
        initObservers()
        makeApiCalls()
    }

    private fun makeApiCalls() {
        getRandomMeal()
        getCategories()
        getRegions()
    }

    private fun getRandomMeal() {
        viewModelScope.launch {
            val response = homeRepository.getRandomMeal()
            randomMealResponse.emit(response)
        }
    }

    private fun getCategories() {
        viewModelScope.launch {
            val response = getCategories.execute()
            categoriesResponse.emit(response)
        }
    }

    private fun getRegions() {
        viewModelScope.launch {
            val response = getRegions.execute()
            regionsResponse.emit(response)
        }
    }

    private fun initObservers() {
        viewModelScope.launch {
            randomMealResponse.collect(::onRandomMealResponse)
        }

        viewModelScope.launch {
            categoriesResponse.collect(::onCategoriesResponse)
        }

        viewModelScope.launch {
            regionsResponse.collect(::onRegionsResponse)
        }
    }

    private fun onRandomMealResponse(response: ApiResponse<MealDetailsModel?>) {
        when (response) {
            is ApiResponse.Success -> randomMeal.value = response.data
            is ApiResponse.Failure -> showErrorMessage("Failed to fetch today's meal")
        }
    }

    private fun onCategoriesResponse(response: ApiResponse<List<CategoryModel>>) {
        when (response) {
            is ApiResponse.Success -> categories.value = response.data
            is ApiResponse.Failure -> showErrorMessage("Failed to fetch categories")
        }
    }

    private fun onRegionsResponse(response: ApiResponse<List<RegionModel>>) {
        when (response) {
            is ApiResponse.Success -> regions.value = response.data
            is ApiResponse.Failure -> showErrorMessage("Failed to fetch regions")
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