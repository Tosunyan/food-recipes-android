package com.example.foodRecipes.domain.repositories

import android.app.Application
import com.example.foodRecipes.data.local.database.MealDao
import com.example.foodRecipes.data.local.database.MealDatabase
import com.example.foodRecipes.data.local.data.MealEntity

class DatabaseRepository(application: Application) {

    private val mealDao: MealDao

    init {
        val database = MealDatabase(application)
        mealDao = database.mealDao()
    }

    fun getMeals() = mealDao.getAllMeals()

    fun searchMeal(search: String?) = mealDao.getMealsByName(search)

    suspend fun insert(meal: MealEntity) = mealDao.insertMeal(meal)

    suspend fun delete(meal: MealEntity) = mealDao.deleteMeal(meal)
}