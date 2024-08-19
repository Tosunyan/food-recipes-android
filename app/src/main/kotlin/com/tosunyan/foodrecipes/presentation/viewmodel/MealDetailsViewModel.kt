package com.tosunyan.foodrecipes.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tosunyan.foodrecipes.datasource.remote.api.onSuccess
import com.tosunyan.foodrecipes.datasource.repository.MealRepository
import com.tosunyan.foodrecipes.domain.mapper.toMealDetailsModel
import com.tosunyan.foodrecipes.domain.model.MealDetailsModel
import com.tosunyan.foodrecipes.domain.model.MealModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MealDetailsViewModel(
    private val repository: MealRepository = MealRepository(),
) : ViewModel() {

    private val _mealDetails = MutableStateFlow(MealDetailsModel())
    val mealDetails = _mealDetails.asStateFlow()

    fun onArgumentsReceive(
        mealModel: MealModel?,
        mealDetailsModel: MealDetailsModel?,
    ) {
        when {
            mealModel != null -> onMealModelReceive(mealModel)
            mealDetailsModel != null -> onMealDetailsReceive(mealDetailsModel)
        }
    }

    fun onSaveButtonClick() {
        viewModelScope.launch {
            if (_mealDetails.value.isSaved) {
                repository.removeSavedMeal(_mealDetails.value)
            } else {
                repository.saveMeal(_mealDetails.value)
            }
            _mealDetails.update { it.copy(isSaved = !it.isSaved) }

            val isSaved = repository.isMealSaved(_mealDetails.value)
            if (_mealDetails.value.isSaved != isSaved) {
                _mealDetails.update { it.copy(isSaved = !isSaved) }
            }
        }
    }

    private fun onMealModelReceive(meal: MealModel) {
        meal.toMealDetailsModel().also {
            setMealDetails(it)
            getMealDetailsFromApi(it.id)
        }
    }

    private fun onMealDetailsReceive(mealDetails: MealDetailsModel) {
        setMealDetails(mealDetails)
    }

    private fun setMealDetails(mealDetails: MealDetailsModel) {
        viewModelScope.launch {
            _mealDetails.update { mealDetails }
        }
    }

    private fun getMealDetailsFromApi(id: String) {
        viewModelScope.launch {
            repository
                .getMealDetails(id)
                .onSuccess { _mealDetails.update { this } }
        }
    }
}