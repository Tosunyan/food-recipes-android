package com.example.foodRecipes.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.foodRecipes.data.local.data.MealEntity
import com.example.foodRecipes.domain.model.MealModel
import com.example.foodRecipes.data.remote.ApiResponse
import com.example.foodRecipes.data.remote.data.MealDetailsDto
import com.example.foodRecipes.data.remote.data.MealsDto
import com.example.foodRecipes.domain.repositories.DatabaseRepository
import com.example.foodRecipes.domain.repositories.DescriptionRepository
import kotlinx.coroutines.launch

class DescriptionFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: DescriptionRepository = DescriptionRepository()
    private val databaseRepository: DatabaseRepository = DatabaseRepository(application)

    fun getMealDetails(id: String): MutableLiveData<ApiResponse<MealDetailsDto>> {
        val liveData = MutableLiveData<ApiResponse<MealDetailsDto>>()

        viewModelScope.launch {
            liveData.value = repository.getMealDetails(id)
        }

        return liveData
    }

    suspend fun insertMeal(meal: MealEntity) = databaseRepository.insert(meal)

    suspend fun deleteMeal(meal: MealEntity) = databaseRepository.delete(meal)
}