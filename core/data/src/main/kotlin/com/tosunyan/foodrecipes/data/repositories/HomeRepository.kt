package com.tosunyan.foodrecipes.data.repositories

import com.tosunyan.foodrecipes.data.mappers.toCategoryModel
import com.tosunyan.foodrecipes.data.mappers.toMealDetailsModel
import com.tosunyan.foodrecipes.data.mappers.toRegionModels
import com.tosunyan.foodrecipes.database.MealDatabase
import com.tosunyan.foodrecipes.model.CategoryModel
import com.tosunyan.foodrecipes.model.MealDetailsModel
import com.tosunyan.foodrecipes.model.RegionModel
import com.tosunyan.foodrecipes.network.api.Api
import com.tosunyan.foodrecipes.network.api.ApiResponse
import com.tosunyan.foodrecipes.network.api.isSuccess
import com.tosunyan.foodrecipes.network.api.makeApiCall
import com.tosunyan.foodrecipes.network.api.mapOnSuccess
import com.tosunyan.foodrecipes.network.data.CategoryDto

class HomeRepository(
    private val database: MealDatabase,
) {

    suspend fun getRandomMeal(): ApiResponse<MealDetailsModel?> {
        val response = randomMealResponse
        if (response != null && response.isSuccess()) {
            return response.mapOnSuccess {
                this!!.copy(isSaved = database.mealDao.checkMealExists(this.id))
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

    suspend fun getCategories(): ApiResponse<List<CategoryModel>> {
        if (categoriesResponse != null && categoriesResponse !is ApiResponse.Failure) {
            return categoriesResponse!!
        }

        return makeApiCall(apiCall = Api.client::getCategories)
            .mapOnSuccess {
                items
                    .map(CategoryDto::toCategoryModel)
                    .sortedBy(CategoryModel::name)
            }
            .also { categoriesResponse = it }
    }

    suspend fun getRegions(): ApiResponse<List<RegionModel>> {
        if (regionsResponse != null && regionsResponse !is ApiResponse.Failure) {
            return regionsResponse!!
        }

        return makeApiCall(apiCall = Api.client::getAreas)
            .mapOnSuccess { items.toRegionModels() }
            .also { regionsResponse = it }
    }

    companion object {

        // Temporary solution for caching the data
        // TODO Use database or other cleaner option
        private var randomMealResponse: ApiResponse<MealDetailsModel?>? = null
        private var categoriesResponse: ApiResponse<List<CategoryModel>>? = null
        private var regionsResponse: ApiResponse<List<RegionModel>>? = null
    }
}