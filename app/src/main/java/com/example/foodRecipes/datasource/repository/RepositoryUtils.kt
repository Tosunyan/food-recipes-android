package com.example.foodRecipes.datasource.repository

import com.example.foodRecipes.datasource.local.data.IngredientEntity
import com.example.foodRecipes.datasource.local.data.MealEntity
import com.example.foodRecipes.datasource.local.database.IngredientDao
import com.example.foodRecipes.datasource.local.database.MealDao
import com.example.foodRecipes.datasource.local.database.MealDatabase
import com.example.foodRecipes.datasource.remote.api.ApiResponse
import com.example.foodRecipes.datasource.remote.api.makeApiCall
import com.example.foodRecipes.datasource.remote.api.mapOnSuccess
import com.example.foodRecipes.domain.mapper.toIngredientEntities
import com.example.foodRecipes.domain.mapper.toMealEntity
import com.example.foodRecipes.domain.model.MealDetailsModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

suspend fun <T, R> getMealsWithSavedStatus(
    mealDao: MealDao,
    apiCall: suspend () -> Response<T>,
    mapper: T.(mealIds: List<String>) -> R
): ApiResponse<R> {
    return withContext(Dispatchers.IO) {
        val apiResponseDeferred = async { makeApiCall(apiCall) }
        val savedIdsDeferred = async { mealDao.getMealIds() }

        val apiResponse = apiResponseDeferred.await()
        val savedIds = savedIdsDeferred.await()

        apiResponse.mapOnSuccess { mapper(savedIds) }
    }
}

suspend fun mealWithIngredientsTransaction(
    database: MealDatabase,
    mealDetails: MealDetailsModel,
    mealTransaction: suspend MealDao.(MealEntity) -> Unit,
    ingredientsTransaction: suspend IngredientDao.(List<IngredientEntity>) -> Unit,
) {
    withContext(Dispatchers.IO) {
        val mealEntity = mealDetails.toMealEntity()
        val ingredientEntities = mealDetails.toIngredientEntities()

        with(database) {
            launch { mealDao.mealTransaction(mealEntity) }
            launch { ingredientDao.ingredientsTransaction(ingredientEntities) }
        }
    }
}