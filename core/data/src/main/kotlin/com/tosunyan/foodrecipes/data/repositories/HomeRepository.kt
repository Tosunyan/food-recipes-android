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

class HomeRepository(
    override val dispatcherProvider: DispatcherProvider,
    private val apiService: ApiService,
    private val database: MealDatabase,
): DispatcherScope {

    private var randomMealResponse: MealDetailsModel? = null
    private var categoriesResponse: List<CategoryModel> = emptyList()
    private var regionsResponse: List<RegionModel> = emptyList()

    init {
        println("${this::class.simpleName}.apiService: ${apiService::class.simpleName}")
    }

    suspend fun getRandomMeal(): MealDetailsModel? {
        randomMealResponse?.let {
            return it.copy(isSaved = database.mealDao.checkMealExists(it.id))
        }

        randomMealResponse = getMealsWithSavedStatus(
            mealDao = database.mealDao,
            apiCall = apiService::getRandomMeal,
            mapper = { items.firstOrNull()?.toMealDetailsModel(it) }
        )

        return randomMealResponse
    }

    suspend fun getCategories(): List<CategoryModel> {
        if (categoriesResponse.isNotEmpty()) return categoriesResponse

        categoriesResponse = withIOResultOrDefault(defaultValue = emptyList()) {
            apiService.getCategories().items
                .map(CategoryDto::toCategoryModel)
                .sortedBy(CategoryModel::name)
        }

        return categoriesResponse
    }

    suspend fun getRegions(): List<RegionModel> {
        if (regionsResponse.isNotEmpty()) return regionsResponse

        regionsResponse = withIOResultOrDefault(defaultValue = emptyList()) {
            apiService.getAreas().items.toRegionModels()
        }

        return regionsResponse
    }
}