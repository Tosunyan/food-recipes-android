package com.example.foodRecipes.presentation.viewmodel

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodRecipes.datasource.remote.api.onSuccess
import com.example.foodRecipes.datasource.repository.MealDetailsRepository
import com.example.foodRecipes.domain.mapper.toMealModel
import com.example.foodRecipes.domain.model.MealModel
import com.example.foodRecipes.presentation.fragment.MealDetailsFragment.Companion.ARG_ID
import com.example.foodRecipes.presentation.fragment.MealDetailsFragment.Companion.ARG_MODEL
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MealDetailsViewModel(
    private val repository: MealDetailsRepository = MealDetailsRepository()
) : ViewModel() {

    private val _mealDetails = MutableStateFlow<MealModel?>(null)
    val mealDetails = _mealDetails.asStateFlow()

    fun onArgumentsReceive(arguments: Bundle) {
        if (arguments[ARG_MODEL] != null) {
            setMealDetailsFromBundle(arguments)
        } else {
            val id = arguments.getString(ARG_ID)!!
            getMealDetailsFromApi(id)
        }
    }

    private fun setMealDetailsFromBundle(bundle: Bundle) {
        _mealDetails.value = bundle.getParcelable(ARG_MODEL)!!
    }

    private fun getMealDetailsFromApi(id: String) {
        viewModelScope.launch {
            repository
                .getMealDetails(id)
                .onSuccess { _mealDetails.value = this.toMealModel() }
        }
    }
}