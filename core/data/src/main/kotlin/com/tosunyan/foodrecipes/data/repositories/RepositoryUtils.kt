package com.tosunyan.foodrecipes.data.repositories

import com.tosunyan.foodrecipes.common.coroutines.DispatcherScope
import com.tosunyan.foodrecipes.common.coroutines.withIOResultOrNull
import com.tosunyan.foodrecipes.common.coroutines.withIOScope
import com.tosunyan.foodrecipes.data.mappers.toIngredientEntities
import com.tosunyan.foodrecipes.data.mappers.toMealEntity
import com.tosunyan.foodrecipes.database.MealDatabase
import com.tosunyan.foodrecipes.database.dao.IngredientDao
import com.tosunyan.foodrecipes.database.dao.MealDao
import com.tosunyan.foodrecipes.database.model.IngredientEntity
import com.tosunyan.foodrecipes.database.model.MealEntity
import com.tosunyan.foodrecipes.model.MealDetailsModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

suspend fun <T, R> DispatcherScope.getMealsWithSavedStatus(
    mealDao: MealDao,
    apiCall: suspend () -> T,
    mapper: T.(mealIds: List<String>) -> R
): R? {
    return withIOResultOrNull {
        val apiResponseDeferred = async { apiCall() }
        val savedIdsDeferred = async { mealDao.getMealIds() }

        val apiResponse = apiResponseDeferred.await()
        val savedIds = savedIdsDeferred.await()

        apiResponse.mapper(savedIds)
    }
}

suspend fun DispatcherScope.mealWithIngredientsTransaction(
    database: MealDatabase,
    mealDetails: MealDetailsModel,
    mealTransaction: suspend MealDao.(MealEntity) -> Unit,
    ingredientsTransaction: suspend IngredientDao.(List<IngredientEntity>) -> Unit,
) {
    withIOScope {
        val mealEntity = mealDetails.toMealEntity()
        val ingredientEntities = mealDetails.toIngredientEntities()

        with(database) {
            launch { mealDao.mealTransaction(mealEntity) }
            launch { ingredientDao.ingredientsTransaction(ingredientEntities) }
        }
    }
}