package com.tosunyan.foodrecipes.data.repositories

import com.tosunyan.foodrecipes.common.coroutines.DispatcherProvider
import com.tosunyan.foodrecipes.common.coroutines.DispatcherScope
import com.tosunyan.foodrecipes.common.coroutines.withIOResultOrDefault
import com.tosunyan.foodrecipes.data.mappers.toCategoryModel
import com.tosunyan.foodrecipes.data.mappers.toMealDetailsModel
import com.tosunyan.foodrecipes.data.mappers.toRegionModels
import com.tosunyan.foodrecipes.database.MealDatabase
import com.tosunyan.foodrecipes.model.CategoryModel
import com.tosunyan.foodrecipes.model.MealDetailsModel
import com.tosunyan.foodrecipes.model.RegionModel
import com.tosunyan.foodrecipes.network.api.ApiService
import com.tosunyan.foodrecipes.network.data.CategoryDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class HomeRepository(
    override val dispatcherProvider: DispatcherProvider,
    private val apiService: ApiService,
    private val database: MealDatabase,
): DispatcherScope {

    init {
        println("${this::class.simpleName}.apiService: ${apiService::class.simpleName}")
    }

    fun observeDailySpecial(): Flow<MealDetailsModel?> {
        return database.mealDao.getAllMeals().map { getDailySpecial() }
    }

    suspend fun getCategories(): List<CategoryModel> {
        categoriesResponse.takeIf { it.isNotEmpty() }?.let { return it }

        categoriesResponse = withIOResultOrDefault(defaultValue = emptyList()) {
            apiService.getCategories().items
                .map(CategoryDto::toCategoryModel)
                .sortedBy(CategoryModel::name)
        }

        return categoriesResponse
    }

    suspend fun getRegions(): List<RegionModel> {
        regionsResponse.takeIf { it.isNotEmpty() }?.let { return it }

        regionsResponse = withIOResultOrDefault(defaultValue = emptyList()) {
            apiService.getAreas().items.toRegionModels()
        }

        return regionsResponse
    }

    private suspend fun getDailySpecial(): MealDetailsModel? {
        dailySpecialResponse?.let {
            return it.copy(isSaved = database.mealDao.checkMealExists(it.id))
        }

        dailySpecialResponse = getMealsWithSavedStatus(
            mealDao = database.mealDao,
            apiCall = apiService::getDailySpecial,
            mapper = { items.firstOrNull()?.toMealDetailsModel(it) }
        )

        return dailySpecialResponse
    }

    companion object {

        // Temporary solution for caching the data
        // TODO Use database or other cleaner option
        private var dailySpecialResponse: MealDetailsModel? = null
        private var categoriesResponse: List<CategoryModel> = emptyList()
        private var regionsResponse: List<RegionModel> = emptyList()
    }
}