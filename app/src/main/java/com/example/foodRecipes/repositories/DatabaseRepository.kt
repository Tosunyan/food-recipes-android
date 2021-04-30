package com.example.foodRecipes.repositories

import android.app.Application
import com.example.foodRecipes.database.MealDao
import com.example.foodRecipes.database.MealDatabase
import com.example.foodRecipes.models.Meal

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