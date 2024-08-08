package com.example.foodRecipes.presentation.viewmodel

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodRecipes.datasource.remote.api.onSuccess
import com.example.foodRecipes.datasource.repository.MealRepository
import com.example.foodRecipes.domain.mapper.toMealDetailsModel
import com.example.foodRecipes.domain.model.MealDetailsModel
import com.example.foodRecipes.domain.model.MealModel
import com.example.foodRecipes.presentation.fragment.MealDetailsFragment.Companion.ARG_MEAL_DETAILS_MODEL
import com.example.foodRecipes.presentation.fragment.MealDetailsFragment.Companion.ARG_MEAL_MODEL
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class MealDetailsViewModel(
    private val repository: MealRepository = MealRepository(),
) : ViewModel() {

    private val _mealDetails = MutableStateFlow(MealDetailsModel())
    val mealDetails = _mealDetails.asStateFlow()

    fun onArgumentsReceive(arguments: Bundle) {
        when {
            arguments.containsKey(ARG_MEAL_DETAILS_MODEL) -> {
                arguments.getMealDetailsModel()
                    ?.let(::setMealDetails)
            }
            arguments.containsKey(ARG_MEAL_MODEL) -> {
                arguments.getMealModel()
                    ?.toMealDetailsModel()
                    ?.let {
                        setMealDetails(it)
                        getMealDetailsFromApi(it.id)
                    }
            }
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

    private fun setMealDetails(mealDetails: MealDetailsModel) {
        viewModelScope.launch {
            delay(0.2.seconds) // To make animations work
            _mealDetails.update { mealDetails }
        }
    }

    private fun getMealDetailsFromApi(id: String) {
        viewModelScope.launch {
            delay(0.2.seconds)
            repository
                .getMealDetails(id)
                .onSuccess { _mealDetails.update { this } }
        }
    }

    private fun Bundle.getMealDetailsModel(): MealDetailsModel? {
        return getParcelable(ARG_MEAL_DETAILS_MODEL)
    }

    private fun Bundle.getMealModel(): MealModel? {
        return getParcelable(ARG_MEAL_MODEL)
    }
}