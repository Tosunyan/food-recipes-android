package com.example.foodRecipes.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.foodRecipes.data.models.Meal

@Dao
interface MealDao {

    @Insert
    suspend fun insertMeal(meal: Meal)

    @Delete
    suspend fun deleteMeal(meal: Meal)

    @Query("SELECT * FROM Meal ORDER BY strMeal")
    fun getAllMeals(): LiveData<List<Meal>>

    @Query("SELECT * FROM Meal WHERE strMeal LIKE '%' || :search || '%' ORDER BY strMeal")
    fun getMealsByName(search: String?): LiveData<List<Meal>>
}