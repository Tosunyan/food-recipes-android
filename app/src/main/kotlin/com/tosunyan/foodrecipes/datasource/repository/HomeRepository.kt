package com.tosunyan.foodrecipes.datasource.repository

import com.tosunyan.foodrecipes.datasource.local.database.DatabaseProvider
import com.tosunyan.foodrecipes.datasource.local.database.MealDatabase
import com.tosunyan.foodrecipes.datasource.remote.api.Api
import com.tosunyan.foodrecipes.datasource.remote.api.ApiResponse
import com.tosunyan.foodrecipes.datasource.remote.api.makeApiCall
import com.tosunyan.foodrecipes.datasource.remote.api.mapOnSuccess
import com.tosunyan.foodrecipes.datasource.remote.data.CategoryDto
import com.tosunyan.foodrecipes.domain.mapper.toCategoryModel
import com.tosunyan.foodrecipes.domain.mapper.toMealDetailsModel
import com.tosunyan.foodrecipes.domain.mapper.toRegionModels
import com.tosunyan.foodrecipes.domain.model.CategoryModel
import com.tosunyan.foodrecipes.domain.model.MealDetailsModel
import com.tosunyan.foodrecipes.domain.model.RegionModel

class HomeRepository(
    private val database: MealDatabase = DatabaseProvider.instance,
) {

    suspend fun getRandomMeal(): ApiResponse<MealDetailsModel?> {
        return getMealsWithSavedStatus(
            mealDao = database.mealDao,
            apiCall = Api.client::getRandomMeal,
            mapper = { items.firstOrNull()?.toMealDetailsModel(it) }
        )
    }

    suspend fun getCategories(): ApiResponse<List<CategoryModel>> {
        val apiResponse = makeApiCall(Api.client::getCategories)

        return apiResponse.mapOnSuccess {
            items
                .map(CategoryDto::toCategoryModel)
                .sortedBy(CategoryModel::name)
        }
    }

    suspend fun getRegions(): ApiResponse<List<RegionModel>> {
        val response = makeApiCall(Api.client::getAreas)

        return response.mapOnSuccess { items.toRegionModels() }
    }
}