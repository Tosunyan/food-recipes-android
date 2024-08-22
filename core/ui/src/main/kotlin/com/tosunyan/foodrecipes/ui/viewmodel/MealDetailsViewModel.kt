package com.tosunyan.foodrecipes.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tosunyan.foodrecipes.data.mappers.toMealDetailsModel
import com.tosunyan.foodrecipes.data.repositories.MealRepository
import com.tosunyan.foodrecipes.model.MealDetailsModel
import com.tosunyan.foodrecipes.model.MealModel
import com.tosunyan.foodrecipes.network.api.onSuccess
import com.tosunyan.foodrecipes.ui.helpers.MealSavingHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MealDetailsViewModel(
    private val repository: MealRepository = MealRepository(),
    private val mealSavingHelper: MealSavingHelper = MealSavingHelper(repository)
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
            mealSavingHelper.toggleSavedState(_mealDetails.value) { isSaved ->
                _mealDetails.update { it.copy(isSaved = isSaved) }
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