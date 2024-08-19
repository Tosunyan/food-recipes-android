package com.tosunyan.foodrecipes.datasource.local.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.tosunyan.foodrecipes.datasource.local.data.MealEntity
import com.tosunyan.foodrecipes.datasource.local.data.MealWithIngredients
import kotlinx.coroutines.flow.Flow

@Dao
interface MealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(meal: MealEntity)

    @Delete
    suspend fun delete(meal: MealEntity)

    @Query("SELECT id FROM meals")
    suspend fun getMealIds(): List<String>

    @Query("SELECT EXISTS(SELECT 1 FROM meals WHERE id = :id)")
    suspend fun checkMealExists(id: String): Boolean

    @Transaction
    @Query("SELECT * FROM meals ORDER BY name")
    fun getAllMeals(): Flow<List<MealWithIngredients>>
}