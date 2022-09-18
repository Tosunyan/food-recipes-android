package com.example.foodRecipes.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.foodRecipes.domain.repository.HomeRepository
import com.example.foodRecipes.presentation.extension.convertToLiveData

class HomeFragmentViewModel(
    private val homeRepository: HomeRepository = HomeRepository()
) : ViewModel() {

    fun getRandomMeal() = convertToLiveData {
        homeRepository.getRandomMeal()
    }

    fun getCategories() = convertToLiveData {
        homeRepository.getCategories()
    }

    fun getAreas() = convertToLiveData {
        homeRepository.getAreas()
    }
}