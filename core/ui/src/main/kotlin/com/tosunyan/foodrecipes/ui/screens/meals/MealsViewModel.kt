package com.tosunyan.foodrecipes.ui.screens.meals

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tosunyan.foodrecipes.common.utils.replace
import com.tosunyan.foodrecipes.data.repositories.MealRepository
import com.tosunyan.foodrecipes.model.CategoryModel
import com.tosunyan.foodrecipes.model.MealModel
import com.tosunyan.foodrecipes.model.RegionModel
import com.tosunyan.foodrecipes.ui.helpers.MealSavingHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MealsViewModel(
    private val mealRepository: MealRepository,
    private val mealSavingHelper: MealSavingHelper,
): ViewModel() {

    private val _screenState = MutableStateFlow(ScreenState())
    val screenState = _screenState.asStateFlow()

    fun onArgumentsReceive(
        category: CategoryModel?,
        region: RegionModel?,
    ) {
        showLoading()

        when {
            category != null -> {
                getMealsByCategory(category)
            }
            region != null -> {
                getMealsByRegion(region)
            }
        }
    }

    fun onSaveIconClick(item: MealModel) {
        viewModelScope.launch {
            mealSavingHelper.toggleSavedState(item) { isSaved ->
                _screenState.update { state ->
                    state.copy(
                        meals = state.meals.replace(item) {
                            it.copy(isSaved = isSaved)
                        }
                    )
                }
            }
        }
    }

    private fun getMealsByCategory(category: CategoryModel) {
        viewModelScope.launch {
            _screenState.update {
                it.copy(
                    title = category.name,
                    description = category.description,
                    thumbnailUrl = category.thumbnail,
                )
            }

            mealRepository
                .filterMealsByCategory(category.name)
                .getOrNull()
                .let { meals ->
                    _screenState.update { it.copy(meals = meals.orEmpty()) }
                }
                .also { hideLoading() }
        }
    }

    private fun getMealsByRegion(region: RegionModel) {
        viewModelScope.launch {
            _screenState.update { it.copy(title = region.name) }

            mealRepository
                .filterMealsByArea(region.name)
                .getOrNull()
                .let { meals ->
                    _screenState.update { it.copy(meals = meals.orEmpty()) }
                }
                .also { hideLoading() }
        }
    }

    private fun showLoading() {
        _screenState.update { state ->
            state.copy(
                isLoading = true,
                meals = buildList {
                    repeat(8) {
                        add(MealModel(id = "$it"))
                    }
                }
            )
        }
    }

    private fun hideLoading() {
        _screenState.update {
            it.copy(isLoading = false)
        }
    }

    data class ScreenState(
        val title: String = "",
        val description: String? = null,
        val thumbnailUrl: String? = null,
        val meals: List<MealModel> = emptyList(),
        val isLoading: Boolean = true,
    )
}