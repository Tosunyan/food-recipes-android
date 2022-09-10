package com.example.foodRecipes.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.foodRecipes.data.local.data.MealEntity
import com.example.foodRecipes.domain.model.MealModel
import com.example.foodRecipes.domain.repositories.DatabaseRepository

class DatabaseViewModel(application: Application): AndroidViewModel(application) {

    private val repository = DatabaseRepository(application)

    suspend fun insertMeal(meal: MealEntity) = repository.insert(meal)

    suspend fun deleteMeal(meal: MealEntity) = repository.delete(meal)

    fun getMealsFromDb(): LiveData<List<MealEntity>> = repository.getMeals()
}