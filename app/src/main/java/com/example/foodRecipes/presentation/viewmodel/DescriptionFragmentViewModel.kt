package com.example.foodRecipes.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.foodRecipes.domain.repository.DescriptionRepository
import com.example.foodRecipes.presentation.extension.convertToLiveData

class DescriptionFragmentViewModel(
    private val repository: DescriptionRepository = DescriptionRepository()
) : ViewModel() {

    fun getMealDetails(id: String) = convertToLiveData {
        repository.getMealDetails(id)
    }
}