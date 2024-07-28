package com.example.foodRecipes.presentation.viewmodel

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodRecipes.datasource.remote.api.onSuccess
import com.example.foodRecipes.datasource.repository.MealRepository
import com.example.foodRecipes.domain.model.MealModel
import com.example.foodRecipes.presentation.fragment.MealsFragment.Action
import com.example.foodRecipes.presentation.fragment.MealsFragment.Companion.ARG_ACTION
import com.example.foodRecipes.presentation.fragment.MealsFragment.Companion.ARG_TITLE
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MealsFragmentViewModel(
    private val mealRepository: MealRepository = MealRepository(),
): ViewModel() {

    private val _title = MutableStateFlow("")
    val title = _title.asStateFlow()

    private val _meals = MutableStateFlow<List<MealModel>>(emptyList())
    val meals = _meals.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {
        showLoading()
    }

    fun onArgumentsReceive(arguments: Bundle) {
        _title.value = arguments.getString(ARG_TITLE)!!

        when (arguments.getSerializable(ARG_ACTION)) {
            Action.CATEGORY -> filterMealsByCategory()
            Action.AREA -> filterMealsByArea()
        }
    }

    private fun filterMealsByCategory() {
        viewModelScope.launch {
            mealRepository
                .filterMealsByCategory(title.value)
                .onSuccess { _meals.value = this }
                .also { _isLoading.value = false }
        }
    }

    private fun filterMealsByArea() {
        viewModelScope.launch {
            mealRepository
                .filterMealsByArea(title.value)
                .onSuccess { _meals.value = this }
                .also { _isLoading.value = false }
        }
    }

    private fun showLoading() {
        _isLoading.value = true
        _meals.value = buildList {
            repeat(8) {
                add(MealModel(id = "$it"))
            }
        }
    }
}