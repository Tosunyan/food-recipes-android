package com.tosunyan.foodrecipes.data.repositories

import com.tosunyan.foodrecipes.common.coroutines.DispatcherProvider
import com.tosunyan.foodrecipes.common.coroutines.DispatcherScope
import com.tosunyan.foodrecipes.common.coroutines.ioFlow
import com.tosunyan.foodrecipes.common.coroutines.withIOResultOrDefault
import com.tosunyan.foodrecipes.common.coroutines.withIOResultOrNull
import com.tosunyan.foodrecipes.data.mappers.toMealDetailsList
import com.tosunyan.foodrecipes.data.mappers.toMealDetailsModel
import com.tosunyan.foodrecipes.data.mappers.toMealModels
import com.tosunyan.foodrecipes.database.MealDatabase
import com.tosunyan.foodrecipes.database.dao.IngredientDao
import com.tosunyan.foodrecipes.database.dao.MealDao
import com.tosunyan.foodrecipes.database.model.MealWithIngredients
import com.tosunyan.foodrecipes.model.MealDetailsModel
import com.tosunyan.foodrecipes.model.MealModel
import com.tosunyan.foodrecipes.model.SaveableMeal
import com.tosunyan.foodrecipes.network.api.ApiService
import com.tosunyan.foodrecipes.network.data.ListDto
import com.tosunyan.foodrecipes.network.data.MealDetailsDto
import com.tosunyan.foodrecipes.network.data.MealDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.map

class MealRepository(
    override val dispatcherProvider: DispatcherProvider,
    private val apiService: ApiService,
    private val database: MealDatabase,
): DispatcherScope {

    init {
        println("${this::class.simpleName}.apiService: ${apiService::class.simpleName}")
    }

    fun getSavedMealsFlow(): Flow<List<MealDetailsModel>> {
        return ioFlow {
            database.mealDao.getAllMeals()
                .map(List<MealWithIngredients>::toMealDetailsList)
                .let { emitAll(it) }
        }
    }

    suspend fun filterMealsByCategory(category: String): List<MealModel> {
        return getMealsWithSavedStatus(
            mealDao = database.mealDao,
            apiCall = { apiService.filterMealsByCategory(category) },
            mapper = ListDto<MealDto>::toMealModels
        ).orEmpty()
    }

    suspend fun filterMealsByArea(area: String): List<MealModel> {
        return getMealsWithSavedStatus(
            mealDao = database.mealDao,
            apiCall = { apiService.filterMealsByArea(area) },
            mapper = ListDto<MealDto>::toMealModels
        ).orEmpty()
    }

    suspend fun getMealDetails(id: String): MealDetailsModel? {
        return getMealsWithSavedStatus(
            mealDao = database.mealDao,
            apiCall = { apiService.getMealDetails(id) },
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
        return withIOResultOrDefault(defaultValue = false) {
            database.mealDao.checkMealExists(meal.id)
        }
    }

    private suspend fun saveMeal(mealModel: MealModel) {
        getMealDetailsWithoutSavedStatus(mealModel.id)
            ?.let { saveMeal(it) }
    }

    private suspend fun saveMeal(mealDetails: MealDetailsModel) {
        insertMealWithIngredients(mealDetails)
    }

    private suspend fun removeSavedMeal(mealModel: MealModel) {
        getMealDetailsWithoutSavedStatus(mealModel.id)
            ?.let { removeSavedMeal(it) }
    }

    private suspend fun removeSavedMeal(mealDetails: MealDetailsModel) {
        deleteMealWithIngredients(mealDetails)
    }

    private suspend fun getMealDetailsWithoutSavedStatus(id: String): MealDetailsModel? {
        return withIOResultOrNull {
            apiService.getMealDetails(id).toMealDetailsModel()
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