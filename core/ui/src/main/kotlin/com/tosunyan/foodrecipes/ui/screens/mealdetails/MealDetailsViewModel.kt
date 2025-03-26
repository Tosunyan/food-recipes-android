package com.tosunyan.foodrecipes.ui.screens.mealdetails

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cafe.adriel.voyager.navigator.Navigator
import com.tosunyan.foodrecipes.data.mappers.toMealDetailsModel
import com.tosunyan.foodrecipes.data.repositories.MealRepository
import com.tosunyan.foodrecipes.model.CategoryModel
import com.tosunyan.foodrecipes.model.MealDetailsModel
import com.tosunyan.foodrecipes.model.MealModel
import com.tosunyan.foodrecipes.model.RegionModel
import com.tosunyan.foodrecipes.ui.helpers.MealSavingHelper
import com.tosunyan.foodrecipes.ui.screens.meals.MealsScreen
import com.tosunyan.foodrecipes.ui.utils.openLink
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MealDetailsViewModel(
    private val repository: MealRepository,
    private val mealSavingHelper: MealSavingHelper,
) : ViewModel() {

    private val _screenState = MutableStateFlow(MealDetailsScreenState())
    val screenState = _screenState.asStateFlow()

    init {
        _screenState.update {
            it.copy(
                onSaveButtonClick = ::onSaveButtonClick,
                onShareButtonClick = ::onShareButtonClick,
                onSharingOptionsDialogDismiss = ::onSharingOptionsDialogDismiss,
            )
        }
    }

    fun setNavigator(navigator: Navigator) {
        _screenState.update { state ->
            state.copy(
                onBackButtonClick = navigator::pop,
                onCategoryClick = {
                    MealsScreen(
                        category = CategoryModel(it, "", "")
                    ).let(navigator::push)
                },
                onRegionClick = {
                    MealsScreen(region = RegionModel(it))
                        .let(navigator::push)
                },
            )
        }
    }

    fun setContext(context: Context) {
        _screenState.update {
            it.copy(
                onYoutubeClick = context::openLink,
                onSourceClick = context::openLink,
            )
        }
    }

    fun onArgumentsReceive(
        mealModel: MealModel?,
        mealDetailsModel: MealDetailsModel?,
    ) {
        when {
            mealModel != null -> onMealModelReceive(mealModel)
            mealDetailsModel != null -> onMealDetailsReceive(mealDetailsModel)
        }
    }

    private fun onSharingOptionsDialogDismiss() {
        _screenState.update { it.copy(isSharingOptionsDialogVisible = false) }
    }

    private fun onShareButtonClick() {
        _screenState.update { it.copy(isSharingOptionsDialogVisible = true) }
    }

    private fun onSaveButtonClick() {
        viewModelScope.launch {
            mealSavingHelper.toggleSavedState(_screenState.value.meal) { isSaved ->
                _screenState.update {
                    it.copy(meal = it.meal.copy(isSaved = isSaved))
                }
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
        _screenState.update { it.copy(meal = mealDetails) }
    }

    private fun getMealDetailsFromApi(id: String) {
        viewModelScope.launch {
            repository.getMealDetails(id).onSuccess {
                _screenState.update { state ->
                    state.copy(meal = it)
                }
            }
        }
    }
}