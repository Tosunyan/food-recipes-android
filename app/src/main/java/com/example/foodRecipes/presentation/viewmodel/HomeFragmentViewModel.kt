package com.example.foodRecipes.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodRecipes.datasource.remote.api.ApiResponse
import com.example.foodRecipes.datasource.repository.HomeRepository
import com.example.foodRecipes.domain.model.CategoryModel
import com.example.foodRecipes.domain.model.MealModel
import com.example.foodRecipes.domain.model.RegionModel
import com.example.foodRecipes.domain.usecase.GetCategories
import com.example.foodRecipes.domain.usecase.GetRegions
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class HomeFragmentViewModel(
    private val getCategories: GetCategories = GetCategories,
    private val getRegions: GetRegions = GetRegions,
    private val homeRepository: HomeRepository = HomeRepository()
) : ViewModel() {

    private val randomMealResponse = MutableSharedFlow<ApiResponse<MealModel?>>()
    private val categoriesResponse = MutableSharedFlow<ApiResponse<List<CategoryModel>>>()
    private val regionsResponse = MutableSharedFlow<ApiResponse<List<RegionModel>>>()

    val randomMeal = MutableStateFlow<MealModel?>(null)
    val categories = MutableStateFlow<List<CategoryModel>>(emptyList())
    val regions = MutableStateFlow<List<RegionModel>>(emptyList())

    val showErrorMessage = MutableSharedFlow<String>()

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

    private fun onRandomMealResponse(response: ApiResponse<MealModel?>) {
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
            showErrorMessage.emit(message)
        }
    }
}