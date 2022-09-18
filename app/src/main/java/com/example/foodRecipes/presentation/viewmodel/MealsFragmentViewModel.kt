package com.example.foodRecipes.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.foodRecipes.datasource.local.data.MealEntity
import com.example.foodRecipes.datasource.remote.api.ApiResponse
import com.example.foodRecipes.datasource.remote.data.MealsDto
import com.example.foodRecipes.domain.repository.DatabaseRepository
import com.example.foodRecipes.domain.repository.MealRepository
import kotlinx.coroutines.launch

class MealsFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private val mealRepository = MealRepository()
    private val databaseRepository = DatabaseRepository(application)

    fun filterMealsByCategory(category: String?): LiveData<ApiResponse<MealsDto>> {
        val liveData = MutableLiveData<ApiResponse<MealsDto>>()

        viewModelScope.launch {
            liveData.value = mealRepository.filterMealsByCategory(category)
        }

        return liveData
    }

    fun filterMealsByArea(area: String?): LiveData<ApiResponse<MealsDto>> {
        val liveData = MutableLiveData<ApiResponse<MealsDto>>()

        viewModelScope.launch {
            liveData.value = mealRepository.filterMealsByArea(area)
        }

        return liveData
    }


    fun getMealsFromDb() = databaseRepository.getMeals()

    suspend fun insertMeal(meal: MealEntity) = databaseRepository.insert(meal)

    suspend fun deleteMeal(meal: MealEntity) = databaseRepository.delete(meal)
}