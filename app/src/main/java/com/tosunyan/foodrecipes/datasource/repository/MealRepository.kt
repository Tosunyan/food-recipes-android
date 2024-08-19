package com.tosunyan.foodrecipes.datasource.repository

import com.tosunyan.foodrecipes.datasource.local.data.MealWithIngredients
import com.tosunyan.foodrecipes.datasource.local.database.DatabaseProvider
import com.tosunyan.foodrecipes.datasource.local.database.IngredientDao
import com.tosunyan.foodrecipes.datasource.local.database.MealDao
import com.tosunyan.foodrecipes.datasource.local.database.MealDatabase
import com.tosunyan.foodrecipes.datasource.remote.api.Api
import com.tosunyan.foodrecipes.datasource.remote.api.ApiResponse
import com.tosunyan.foodrecipes.datasource.remote.data.ListDto
import com.tosunyan.foodrecipes.datasource.remote.data.MealDetailsDto
import com.tosunyan.foodrecipes.datasource.remote.data.MealDto
import com.tosunyan.foodrecipes.domain.mapper.toMealDetailsList
import com.tosunyan.foodrecipes.domain.mapper.toMealDetailsModel
import com.tosunyan.foodrecipes.domain.mapper.toMealModels
import com.tosunyan.foodrecipes.domain.model.MealDetailsModel
import com.tosunyan.foodrecipes.domain.model.MealModel
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