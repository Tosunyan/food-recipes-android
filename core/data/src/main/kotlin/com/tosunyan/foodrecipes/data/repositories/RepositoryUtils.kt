package com.tosunyan.foodrecipes.data.repositories

import com.tosunyan.foodrecipes.data.mappers.toIngredientEntities
import com.tosunyan.foodrecipes.data.mappers.toMealEntity
import com.tosunyan.foodrecipes.database.MealDatabase
import com.tosunyan.foodrecipes.database.dao.IngredientDao
import com.tosunyan.foodrecipes.database.dao.MealDao
import com.tosunyan.foodrecipes.database.model.IngredientEntity
import com.tosunyan.foodrecipes.database.model.MealEntity
import com.tosunyan.foodrecipes.model.MealDetailsModel
import com.tosunyan.foodrecipes.network.api.ApiResponse
import com.tosunyan.foodrecipes.network.api.makeApiCall
import com.tosunyan.foodrecipes.network.api.mapOnSuccess
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