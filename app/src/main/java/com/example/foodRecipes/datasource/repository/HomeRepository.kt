package com.example.foodRecipes.datasource.repository

import com.example.foodRecipes.datasource.local.database.DatabaseProvider
import com.example.foodRecipes.datasource.local.database.MealDatabase
import com.example.foodRecipes.datasource.remote.api.Api
import com.example.foodRecipes.datasource.remote.api.ApiResponse
import com.example.foodRecipes.datasource.remote.api.makeApiCall
import com.example.foodRecipes.datasource.remote.api.mapOnSuccess
import com.example.foodRecipes.datasource.remote.data.CategoryDto
import com.example.foodRecipes.domain.mapper.toCategoryModel
import com.example.foodRecipes.domain.mapper.toMealDetailsModel
import com.example.foodRecipes.domain.mapper.toRegionModels
import com.example.foodRecipes.domain.model.CategoryModel
import com.example.foodRecipes.domain.model.MealDetailsModel
import com.example.foodRecipes.domain.model.RegionModel

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
            categories
                .map(CategoryDto::toCategoryModel)
                .sortedBy(CategoryModel::name)
        }
    }

    suspend fun getRegions(): ApiResponse<List<RegionModel>> {
        val response = makeApiCall(Api.client::getAreas)

        return response.mapOnSuccess { items.toRegionModels() }
    }
}