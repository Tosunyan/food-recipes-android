package com.example.foodRecipes.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.foodRecipes.models.Meal
import com.example.foodRecipes.repositories.DatabaseRepository

class DatabaseViewModel(application: Application): AndroidViewModel(application) {

    private val repository = DatabaseRepository(application)

    suspend fun insertMeal(meal: Meal) = repository.insert(meal)

    suspend fun deleteMeal(meal: Meal) = repository.delete(meal)

    fun getMealsFromDb(): LiveData<List<Meal>> = repository.getMeals()
}