package com.example.foodRecipes.datasource.repository

import com.example.foodRecipes.datasource.remote.api.Api
import com.example.foodRecipes.datasource.remote.api.ApiResponse
import com.example.foodRecipes.datasource.remote.api.makeApiCall
import com.example.foodRecipes.datasource.remote.api.mapOnSuccess
import com.example.foodRecipes.datasource.remote.data.CategoryDto
import com.example.foodRecipes.domain.mapper.toCategoryModel
import com.example.foodRecipes.domain.mapper.toMealModel
import com.example.foodRecipes.domain.mapper.toRegionModels
import com.example.foodRecipes.domain.model.CategoryModel
import com.example.foodRecipes.domain.model.MealModel
import com.example.foodRecipes.domain.model.RegionModel

class HomeRepository {

    suspend fun getRandomMeal(): ApiResponse<MealModel?> {
        val apiResponse = makeApiCall(Api.client::getRandomMeal)

        return apiResponse.mapOnSuccess {
            items?.firstOrNull()?.toMealModel()
        }
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

        return response.mapOnSuccess { items?.toRegionModels().orEmpty() }
    }
}