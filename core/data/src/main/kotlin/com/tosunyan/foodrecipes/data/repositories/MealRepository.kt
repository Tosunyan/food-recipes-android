package com.tosunyan.foodrecipes.data.repositories

import com.tosunyan.foodrecipes.data.mappers.toMealDetailsList
import com.tosunyan.foodrecipes.data.mappers.toMealDetailsModel
import com.tosunyan.foodrecipes.data.mappers.toMealModels
import com.tosunyan.foodrecipes.database.DatabaseProvider
import com.tosunyan.foodrecipes.database.MealDatabase
import com.tosunyan.foodrecipes.database.dao.IngredientDao
import com.tosunyan.foodrecipes.database.dao.MealDao
import com.tosunyan.foodrecipes.database.model.MealWithIngredients
import com.tosunyan.foodrecipes.model.MealDetailsModel
import com.tosunyan.foodrecipes.model.MealModel
import com.tosunyan.foodrecipes.network.api.Api
import com.tosunyan.foodrecipes.network.api.ApiResponse
import com.tosunyan.foodrecipes.network.data.ListDto
import com.tosunyan.foodrecipes.network.data.MealDetailsDto
import com.tosunyan.foodrecipes.network.data.MealDto
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