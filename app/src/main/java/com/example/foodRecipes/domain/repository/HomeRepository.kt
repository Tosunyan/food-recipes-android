package com.example.foodRecipes.domain.repository

import com.example.foodRecipes.datasource.remote.api.Api
import com.example.foodRecipes.datasource.remote.api.ApiResponse
import com.example.foodRecipes.datasource.remote.api.makeApiCall
import com.example.foodRecipes.datasource.remote.data.CategoryDto
import com.example.foodRecipes.domain.mapper.toCategoryModel
import com.example.foodRecipes.domain.mapper.toMealModel
import com.example.foodRecipes.domain.model.CategoryModel
import com.example.foodRecipes.domain.model.MealModel

class HomeRepository {

    suspend fun getRandomMeal(): ApiResponse<MealModel?> {
        return when(val apiResponse = makeApiCall(Api.client::getRandomMeal)) {
            is ApiResponse.Success -> {
                val mealModel = apiResponse.data.meals?.firstOrNull()?.toMealModel()
                ApiResponse.Success(mealModel)
            }
            is ApiResponse.Failure -> {
                apiResponse
            }
        }
    }

    suspend fun getCategories(): ApiResponse<List<CategoryModel>> {
        return when(val apiResponse = makeApiCall(Api.client::getCategories)) {
            is ApiResponse.Success -> {
                val categories = apiResponse.data.categories
                    .map(CategoryDto::toCategoryModel)
                    .sortedBy(CategoryModel::name)

                ApiResponse.Success(categories)
            }
            is ApiResponse.Failure -> {
                apiResponse
            }
        }
    }

    suspend fun getAreas() = makeApiCall {
        Api.client.getAreas()
    }
}