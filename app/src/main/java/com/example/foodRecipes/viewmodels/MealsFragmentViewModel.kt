package com.example.foodRecipes.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.foodRecipes.models.Meal
import com.example.foodRecipes.repositories.DatabaseRepository
import com.example.foodRecipes.repositories.DescriptionRepository
import com.example.foodRecipes.repositories.MealRepository
import com.example.foodRecipes.responses.MealResponse

class MealsFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private val mealRepository = MealRepository()
    private val databaseRepository = DatabaseRepository(application)
    private val descriptionRepository = DescriptionRepository()

    fun filterMealsByCategory(category: String?): LiveData<MealResponse?> =
        mealRepository.filterMealsByCategory(category)

    fun filterMealsByArea(area: String?): LiveData<MealResponse?> =
        mealRepository.filterMealsByArea(area)

    fun getMealInfo(id: String?): LiveData<MealResponse> =
        descriptionRepository.getMealInfo(id)


    fun getMealsFromDb() = databaseRepository.getMeals()

    suspend fun insertMeal(meal: Meal) = databaseRepository.insert(meal)

    suspend fun deleteMeal(meal: Meal) = databaseRepository.delete(meal)
}