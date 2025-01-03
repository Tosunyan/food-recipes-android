package com.tosunyan.foodrecipes.data.repositories

import com.tosunyan.foodrecipes.data.mappers.toCategoryModel
import com.tosunyan.foodrecipes.data.mappers.toMealDetailsModel
import com.tosunyan.foodrecipes.data.mappers.toRegionModels
import com.tosunyan.foodrecipes.database.MealDatabase
import com.tosunyan.foodrecipes.model.CategoryModel
import com.tosunyan.foodrecipes.model.MealDetailsModel
import com.tosunyan.foodrecipes.model.RegionModel
import com.tosunyan.foodrecipes.network.api.Api
import com.tosunyan.foodrecipes.network.api.makeApiCall
import com.tosunyan.foodrecipes.network.data.CategoryDto

class HomeRepository(
    private val database: MealDatabase,
) {

    suspend fun getRandomMeal(): Result<MealDetailsModel?> {
        val response = randomMealResponse
        if (response != null && response.isSuccess) {
            return response.map {
                it?.copy(isSaved = database.mealDao.checkMealExists(it.id))
            }
        }

        return getMealsWithSavedStatus(
            mealDao = database.mealDao,
            apiCall = Api.client::getRandomMeal,
            mapper = { items.firstOrNull()?.toMealDetailsModel(it) }
        ).also {
            randomMealResponse = it
        }
    }

    suspend fun getCategories(): Result<List<CategoryModel>> {
        categoriesResponse?.let {
            if (it.isSuccess) return it
        }

        return makeApiCall(apiCall = Api.client::getCategories)
            .map {
                it.items
                    .map(CategoryDto::toCategoryModel)
                    .sortedBy(CategoryModel::name)
            }
            .also { categoriesResponse = it }
    }

    suspend fun getRegions(): Result<List<RegionModel>> {
        regionsResponse?.let {
            if (it.isSuccess) return it
        }

        return makeApiCall(apiCall = Api.client::getAreas)
            .map { it.items.toRegionModels() }
            .also { regionsResponse = it }
    }

    companion object {

        // Temporary solution for caching the data
        // TODO Use database or other cleaner option
        private var randomMealResponse: Result<MealDetailsModel?>? = null
        private var categoriesResponse: Result<List<CategoryModel>>? = null
        private var regionsResponse: Result<List<RegionModel>>? = null
    }
}