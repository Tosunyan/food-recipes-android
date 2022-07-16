package com.example.foodRecipes.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodRecipes.data.remote.ApiResponse
import com.example.foodRecipes.data.remote.responses.CategoryResponse
import com.example.foodRecipes.data.remote.responses.MealResponse
import com.example.foodRecipes.domain.repositories.HomeRepository
import kotlinx.coroutines.launch

class HomeFragmentViewModel : ViewModel() {

    private val repository = HomeRepository()

    fun getRandomMeal(): LiveData<ApiResponse<MealResponse>> {
        val liveData = MutableLiveData<ApiResponse<MealResponse>>()

        viewModelScope.launch {
            liveData.value = repository.getRandomMeal()
        }

        return liveData
    }

    fun getCategories(): LiveData<ApiResponse<CategoryResponse>> {
        val liveData = MutableLiveData<ApiResponse<CategoryResponse>>()

        viewModelScope.launch {
            liveData.value = repository.getCategories()
        }

        return liveData
    }

    fun getAreas(): LiveData<ApiResponse<MealResponse>> {
        val liveData = MutableLiveData<ApiResponse<MealResponse>>()

        viewModelScope.launch {
            liveData.value = repository.getAreas()
        }

        return liveData
    }
}