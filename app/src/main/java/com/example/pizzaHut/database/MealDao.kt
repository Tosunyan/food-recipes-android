package com.example.pizzaHut.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.pizzaHut.models.Meal

@Dao
interface MealDao {

    @Insert
    fun insertMeal(meal: Meal)

    @Delete
    fun deleteMeal(meal: Meal)

    @Query("SELECT * FROM Meal ORDER BY strMeal ASC")
    fun getAllMeals(): LiveData<List<Meal>>
}