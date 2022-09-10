package com.example.foodRecipes.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.example.foodRecipes.domain.repositories.SearchRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.foodRecipes.data.remote.ApiResponse
import com.example.foodRecipes.data.remote.data.MealsDto
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    private val repository: SearchRepository = SearchRepository()

    fun getMealsByFirstLetter(letter: Char): LiveData<ApiResponse<MealsDto>> {
        val liveData = MutableLiveData<ApiResponse<MealsDto>>()

        viewModelScope.launch {
            liveData.value = repository.searchByFirstLetter(letter)
        }

        return liveData
    }

    fun getMealsByName(name: String?): LiveData<ApiResponse<MealsDto>> {
        val liveData = MutableLiveData<ApiResponse<MealsDto>>()

        viewModelScope.launch {
            liveData.value = repository.searchByName(name)
        }

        return liveData
    }
}