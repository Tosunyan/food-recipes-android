package com.tosunyan.foodrecipes.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tosunyan.foodrecipes.data.repositories.MealRepository
import com.tosunyan.foodrecipes.model.CategoryModel
import com.tosunyan.foodrecipes.model.MealModel
import com.tosunyan.foodrecipes.model.RegionModel
import com.tosunyan.foodrecipes.network.api.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MealsViewModel(
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

    fun onArgumentsReceive(
        category: CategoryModel?,
        region: RegionModel?,
    ) {
        _title.value = category?.name ?: region?.name ?: return

        category?.let { filterMealsByCategory() }
        region?.let { filterMealsByArea() }
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