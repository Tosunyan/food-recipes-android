package com.example.foodRecipes.datasource.local.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.foodRecipes.datasource.local.data.MealEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MealDao {

    @Insert
    suspend fun insertMeal(meal: MealEntity)

    @Delete
    suspend fun deleteMeal(meal: MealEntity)

    @Query("SELECT * FROM MealEntity ORDER BY name")
    fun getAllMeals(): Flow<List<MealEntity>>

    @Query("SELECT * FROM MealEntity WHERE name LIKE '%' || :search || '%' ORDER BY name")
    fun getMealsByName(search: String?): Flow<List<MealEntity>>
}