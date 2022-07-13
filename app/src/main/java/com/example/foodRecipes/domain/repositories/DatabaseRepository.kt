package com.example.foodRecipes.domain.repositories

import android.app.Application
import com.example.foodRecipes.data.local.MealDao
import com.example.foodRecipes.data.local.MealDatabase
import com.example.foodRecipes.data.models.Meal

class DatabaseRepository(application: Application) {

    private val mealDao: MealDao

    init {
        val database = MealDatabase(application)
        mealDao = database.mealDao()
    }

    fun getMeals() = mealDao.getAllMeals()

    fun searchMeal(search: String?) = mealDao.getMealsByName(search)

    suspend fun insert(meal: Meal) = mealDao.insertMeal(meal)

    suspend fun delete(meal: Meal) = mealDao.deleteMeal(meal)
}