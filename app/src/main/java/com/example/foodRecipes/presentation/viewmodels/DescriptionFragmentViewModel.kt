package com.example.foodRecipes.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.foodRecipes.data.models.Meal
import com.example.foodRecipes.data.remote.ApiResponse
import com.example.foodRecipes.domain.repositories.DatabaseRepository
import com.example.foodRecipes.domain.repositories.DescriptionRepository
import com.example.foodRecipes.data.remote.responses.MealResponse
import kotlinx.coroutines.launch

class DescriptionFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: DescriptionRepository = DescriptionRepository()
    private val databaseRepository: DatabaseRepository = DatabaseRepository(application)

    fun getMealDetails(id: String): MutableLiveData<ApiResponse<MealResponse>> {
        val liveData = MutableLiveData<ApiResponse<MealResponse>>()

        viewModelScope.launch {
            liveData.value = repository.getMealDetails(id)
        }

        return liveData
    }

    suspend fun insertMeal(meal: Meal) = databaseRepository.insert(meal)

    suspend fun deleteMeal(meal: Meal) = databaseRepository.delete(meal)
}