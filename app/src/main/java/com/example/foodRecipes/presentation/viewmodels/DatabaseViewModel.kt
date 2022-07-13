package com.example.foodRecipes.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.foodRecipes.data.models.Meal
import com.example.foodRecipes.domain.repositories.DatabaseRepository

class DatabaseViewModel(application: Application): AndroidViewModel(application) {

    private val repository = DatabaseRepository(application)

    suspend fun insertMeal(meal: Meal) = repository.insert(meal)

    suspend fun deleteMeal(meal: Meal) = repository.delete(meal)

    fun getMealsFromDb(): LiveData<List<Meal>> = repository.getMeals()
}