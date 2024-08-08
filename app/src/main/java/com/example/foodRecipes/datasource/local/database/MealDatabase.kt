package com.example.foodRecipes.datasource.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.foodRecipes.datasource.local.data.IngredientEntity
import com.example.foodRecipes.datasource.local.data.MealEntity

@Database(
    entities = [
        MealEntity::class,
        IngredientEntity::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class MealDatabase : RoomDatabase() {

    abstract val mealDao: MealDao
    abstract val ingredientDao: IngredientDao
}