package com.example.foodRecipes.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.foodRecipes.data.models.Meal
import com.example.foodRecipes.domain.repositories.DatabaseRepository
import com.example.foodRecipes.domain.repositories.DescriptionRepository
import com.example.foodRecipes.data.remote.responses.MealResponse

class DescriptionFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: DescriptionRepository = DescriptionRepository()
    private val databaseRepository: DatabaseRepository = DatabaseRepository(application)

    fun getMealInfo(id: String?): LiveData<MealResponse> = repository.getMealInfo(id)

    suspend fun insertMeal(meal: Meal) = databaseRepository.insert(meal)

    suspend fun deleteMeal(meal: Meal) = databaseRepository.delete(meal)
}