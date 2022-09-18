package com.example.foodRecipes.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.foodRecipes.datasource.local.data.MealEntity
import com.example.foodRecipes.domain.repository.DatabaseRepository

class DatabaseViewModel(application: Application): AndroidViewModel(application) {

    private val repository = DatabaseRepository(application)

    suspend fun insertMeal(meal: MealEntity) = repository.insert(meal)

    suspend fun deleteMeal(meal: MealEntity) = repository.delete(meal)

    fun getMealsFromDb(): LiveData<List<MealEntity>> = repository.getMeals()
}