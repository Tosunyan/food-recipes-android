package com.example.foodRecipes.datasource.repository

import com.example.foodRecipes.datasource.local.data.MealWithIngredients
import com.example.foodRecipes.datasource.local.database.DatabaseProvider
import com.example.foodRecipes.datasource.local.database.IngredientDao
import com.example.foodRecipes.datasource.local.database.MealDao
import com.example.foodRecipes.datasource.local.database.MealDatabase
import com.example.foodRecipes.datasource.remote.api.Api
import com.example.foodRecipes.datasource.remote.api.ApiResponse
import com.example.foodRecipes.datasource.remote.data.ListDto
import com.example.foodRecipes.datasource.remote.data.MealDetailsDto
import com.example.foodRecipes.datasource.remote.data.MealDto
import com.example.foodRecipes.domain.mapper.toMealDetailsList
import com.example.foodRecipes.domain.mapper.toMealDetailsModel
import com.example.foodRecipes.domain.mapper.toMealModels
import com.example.foodRecipes.domain.model.MealDetailsModel
import com.example.foodRecipes.domain.model.MealModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class MealRepository(
    private val database: MealDatabase = DatabaseProvider.instance,
) {

    fun getSavedMealsFlow(): Flow<List<MealDetailsModel>> {
        return database.mealDao.getAllMeals()
            .flowOn(Dispatchers.IO)
            .map(List<MealWithIngredients>::toMealDetailsList)
            .catch { emit(emptyList()) }
    }

    suspend fun filterMealsByCategory(category: String): ApiResponse<List<MealModel>> {
        return getMealsWithSavedStatus(
            mealDao = database.mealDao,
            apiCall = { Api.client.filterMealsByCategory(category) },
            mapper = ListDto<MealDto>::toMealModels
        )
    }

    suspend fun filterMealsByArea(area: String): ApiResponse<List<MealModel>> {
        return getMealsWithSavedStatus(
            mealDao = database.mealDao,
            apiCall = { Api.client.filterMealsByArea(area) },
            mapper = ListDto<MealDto>::toMealModels
        )
    }

    suspend fun getMealDetails(id: String): ApiResponse<MealDetailsModel> {
        return getMealsWithSavedStatus(
            mealDao = database.mealDao,
            apiCall = { Api.client.getMealDetails(id) },
            mapper = ListDto<MealDetailsDto>::toMealDetailsModel
        )
    }

    suspend fun saveMeal(mealDetails: MealDetailsModel) {
        insertMealWithIngredients(mealDetails)
    }

    suspend fun removeSavedMeal(mealDetails: MealDetailsModel) {
        deleteMealWithIngredients(mealDetails)
    }

    suspend fun isMealSaved(mealDetails: MealDetailsModel): Boolean {
        return withContext(Dispatchers.IO) {
            database.mealDao.checkMealExists(mealDetails.id)
        }
    }

    private suspend fun insertMealWithIngredients(mealDetails: MealDetailsModel) {
        mealWithIngredientsTransaction(
            database = database,
            mealDetails = mealDetails,
            mealTransaction = MealDao::insert,
            ingredientsTransaction = IngredientDao::insert
        )
    }

    private suspend fun deleteMealWithIngredients(mealDetails: MealDetailsModel) {
        mealWithIngredientsTransaction(
            database = database,
            mealDetails = mealDetails,
            mealTransaction = MealDao::delete,
            ingredientsTransaction = IngredientDao::delete
        )
    }
}