package com.tosunyan.foodrecipes.data.repositories

import com.tosunyan.foodrecipes.common.coroutines.DispatcherProvider
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
import com.tosunyan.foodrecipes.model.SaveableMeal
import com.tosunyan.foodrecipes.network.api.Api
import com.tosunyan.foodrecipes.network.api.ApiResponse
import com.tosunyan.foodrecipes.network.api.makeApiCall
import com.tosunyan.foodrecipes.network.api.mapOnSuccess
import com.tosunyan.foodrecipes.network.api.onSuccess
import com.tosunyan.foodrecipes.network.data.ListDto
import com.tosunyan.foodrecipes.network.data.MealDetailsDto
import com.tosunyan.foodrecipes.network.data.MealDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class MealRepository(
    private val database: MealDatabase = DatabaseProvider.instance,
    private val dispatcher: DispatcherProvider = DispatcherProvider.default
) {

    fun getSavedMealsFlow(): Flow<List<MealDetailsModel>> {
        return database.mealDao.getAllMeals()
            .flowOn(dispatcher.io)
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

    suspend fun saveMeal(meal: SaveableMeal) {
        when (meal) {
            is MealDetailsModel -> saveMeal(meal)
            is MealModel -> saveMeal(meal)
        }
    }

    suspend fun removeSavedMeal(meal: SaveableMeal) {
        when (meal) {
            is MealDetailsModel -> removeSavedMeal(meal)
            is MealModel -> removeSavedMeal(meal)
        }
    }

    suspend fun isMealSaved(meal: SaveableMeal): Boolean {
        return withContext(dispatcher.io) {
            database.mealDao.checkMealExists(meal.id)
        }
    }

    private suspend fun saveMeal(mealModel: MealModel) {
        getMealDetailsWithoutSavedStatus(mealModel.id)
            .onSuccess { saveMeal(this) }
    }

    private suspend fun saveMeal(mealDetails: MealDetailsModel) {
        insertMealWithIngredients(mealDetails)
    }

    private suspend fun removeSavedMeal(mealModel: MealModel) {
        getMealDetailsWithoutSavedStatus(mealModel.id)
            .onSuccess { removeSavedMeal(this) }
    }

    private suspend fun removeSavedMeal(mealDetails: MealDetailsModel) {
        deleteMealWithIngredients(mealDetails)
    }

    private suspend fun getMealDetailsWithoutSavedStatus(id: String): ApiResponse<MealDetailsModel> {
        return makeApiCall { Api.client.getMealDetails(id) }
            .mapOnSuccess(ListDto<MealDetailsDto>::toMealDetailsModel)
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