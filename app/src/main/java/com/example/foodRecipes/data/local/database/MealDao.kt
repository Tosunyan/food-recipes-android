package com.example.foodRecipes.data.local.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.foodRecipes.data.local.data.MealEntity

@Dao
interface MealDao {

    @Insert
    suspend fun insertMeal(meal: MealEntity)

    @Delete
    suspend fun deleteMeal(meal: MealEntity)

    @Query("SELECT * FROM MealEntity ORDER BY name")
    fun getAllMeals(): LiveData<List<MealEntity>>

    @Query("SELECT * FROM MealEntity WHERE name LIKE '%' || :search || '%' ORDER BY name")
    fun getMealsByName(search: String?): LiveData<List<MealEntity>>
}