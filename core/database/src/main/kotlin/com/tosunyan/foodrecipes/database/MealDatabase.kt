package com.tosunyan.foodrecipes.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tosunyan.foodrecipes.database.dao.IngredientDao
import com.tosunyan.foodrecipes.database.dao.MealDao
import com.tosunyan.foodrecipes.database.model.IngredientEntity
import com.tosunyan.foodrecipes.database.model.MealEntity

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