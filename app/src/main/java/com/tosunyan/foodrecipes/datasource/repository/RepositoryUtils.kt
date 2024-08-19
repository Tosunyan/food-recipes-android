package com.tosunyan.foodrecipes.datasource.repository

import com.tosunyan.foodrecipes.datasource.local.data.IngredientEntity
import com.tosunyan.foodrecipes.datasource.local.data.MealEntity
import com.tosunyan.foodrecipes.datasource.local.database.IngredientDao
import com.tosunyan.foodrecipes.datasource.local.database.MealDao
import com.tosunyan.foodrecipes.datasource.local.database.MealDatabase
import com.tosunyan.foodrecipes.datasource.remote.api.ApiResponse
import com.tosunyan.foodrecipes.datasource.remote.api.makeApiCall
import com.tosunyan.foodrecipes.datasource.remote.api.mapOnSuccess
import com.tosunyan.foodrecipes.domain.mapper.toIngredientEntities
import com.tosunyan.foodrecipes.domain.mapper.toMealEntity
import com.tosunyan.foodrecipes.domain.model.MealDetailsModel
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