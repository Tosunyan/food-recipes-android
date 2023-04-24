package com.example.foodRecipes.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodRecipes.datasource.remote.api.ApiResponse
import com.example.foodRecipes.datasource.remote.data.CategoriesDto
import com.example.foodRecipes.datasource.remote.data.CategoryDto
import com.example.foodRecipes.datasource.remote.data.RegionDto
import com.example.foodRecipes.datasource.remote.data.RegionsDto
import com.example.foodRecipes.domain.model.CategoryModel
import com.example.foodRecipes.domain.model.MealModel
import com.example.foodRecipes.domain.repository.HomeRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

class HomeFragmentViewModel(
    private val homeRepository: HomeRepository = HomeRepository()
) : ViewModel() {

    private val randomMealResponse = MutableSharedFlow<ApiResponse<MealModel?>>()
    private val categoriesResponse = MutableSharedFlow<ApiResponse<List<CategoryModel>>>()
    private val regionsResponse = MutableSharedFlow<ApiResponse<RegionsDto>>()

    val randomMeal = MutableStateFlow<MealModel?>(null)
    val categories = MutableStateFlow<List<CategoryModel>>(emptyList())
    val regions = MutableStateFlow<List<RegionDto>>(emptyList())

    val showErrorMessage = MutableSharedFlow<String>()

    init {
        makeApiCalls()
        initObservers()
    }

    private fun makeApiCalls() {
        getRandomMeal()
        getCategories()
        getAreas()
    }

    private fun getRandomMeal() {
        viewModelScope.launch {
            val response = homeRepository.getRandomMeal()
            randomMealResponse.emit(response)
        }
    }

    private fun getCategories() {
        viewModelScope.launch {
            val response = homeRepository.getCategories()
            categoriesResponse.emit(response)
        }
    }

    private fun getAreas() {
        viewModelScope.launch {
            val response = homeRepository.getAreas()
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

    private fun onRegionsResponse(response: ApiResponse<RegionsDto>) {
        when (response) {
            is ApiResponse.Success -> regions.value = response.data.meals
            is ApiResponse.Failure -> showErrorMessage("Failed to fetch regions")
        }
    }

    private fun showErrorMessage(message: String) {
        viewModelScope.launch {
            showErrorMessage.emit(message)
        }
    }
}