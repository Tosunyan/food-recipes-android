package com.example.foodRecipes.datasource.local.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.foodRecipes.datasource.local.data.IngredientEntity

@Dao
interface IngredientDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(ingredients: List<IngredientEntity>)

    @Delete
    suspend fun delete(ingredients: List<IngredientEntity>)
}