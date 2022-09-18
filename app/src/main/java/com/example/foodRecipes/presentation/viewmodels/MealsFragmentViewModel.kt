package com.example.foodRecipes.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.foodRecipes.data.local.data.MealEntity
import com.example.foodRecipes.data.remote.api.ApiResponse
import com.example.foodRecipes.data.remote.data.MealsDto
import com.example.foodRecipes.domain.repositories.DatabaseRepository
import com.example.foodRecipes.domain.repositories.MealRepository
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