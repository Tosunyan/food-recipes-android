package com.example.foodRecipes.domain.repository

import android.app.Application
import com.example.foodRecipes.datasource.local.database.MealDao
import com.example.foodRecipes.datasource.local.database.MealDatabase
import com.example.foodRecipes.datasource.local.data.MealEntity

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