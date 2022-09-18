package com.example.foodRecipes.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodRecipes.datasource.remote.api.ApiResponse
import com.example.foodRecipes.datasource.remote.data.CategoriesDto
import com.example.foodRecipes.datasource.remote.data.MealsDto
import com.example.foodRecipes.datasource.remote.data.RegionsDto
import com.example.foodRecipes.domain.repository.HomeRepository
import kotlinx.coroutines.launch

class HomeFragmentViewModel : ViewModel() {

    private val repository = HomeRepository()

    fun getRandomMeal(): LiveData<ApiResponse<MealsDto>> {
        val liveData = MutableLiveData<ApiResponse<MealsDto>>()

        viewModelScope.launch {
            liveData.value = repository.getRandomMeal()
        }

        return liveData
    }

    fun getCategories(): LiveData<ApiResponse<CategoriesDto>> {
        val liveData = MutableLiveData<ApiResponse<CategoriesDto>>()

        viewModelScope.launch {
            liveData.value = repository.getCategories()
        }

        return liveData
    }

    fun getAreas(): LiveData<ApiResponse<RegionsDto>> {
        val liveData = MutableLiveData<ApiResponse<RegionsDto>>()

        viewModelScope.launch {
            liveData.value = repository.getAreas()
        }

        return liveData
    }
}