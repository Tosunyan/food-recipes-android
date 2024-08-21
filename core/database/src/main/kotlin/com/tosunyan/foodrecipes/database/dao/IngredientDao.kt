package com.tosunyan.foodrecipes.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.tosunyan.foodrecipes.database.model.IngredientEntity

@Dao
interface IngredientDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(ingredients: List<IngredientEntity>)

    @Delete
    suspend fun delete(ingredients: List<IngredientEntity>)
}