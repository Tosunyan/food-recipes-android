package com.example.foodRecipes.datasource.repository

import com.example.foodRecipes.datasource.remote.api.Api
import com.example.foodRecipes.datasource.remote.api.ApiResponse
import com.example.foodRecipes.datasource.remote.api.makeApiCall
import com.example.foodRecipes.datasource.remote.api.mapOnSuccess
import com.example.foodRecipes.domain.mapper.toMealModels
import com.example.foodRecipes.domain.model.MealModel

class MealRepository {

    suspend fun filterMealsByCategory(category: String): ApiResponse<List<MealModel>> {
        val response = makeApiCall { Api.client.filterMealsByCategory(category) }

        return response.mapOnSuccess { items?.toMealModels().orEmpty() }
    }

    suspend fun filterMealsByArea(area: String): ApiResponse<List<MealModel>> {
        val response = makeApiCall { Api.client.filterMealsByArea(area) }

        return response.mapOnSuccess { items?.toMealModels().orEmpty() }
    }

    @Suppress("unused")
    suspend fun filterMealsByIngredient(ingredient: String): ApiResponse<List<MealModel>> {
        val response = makeApiCall { Api.client.filterMealsByIngredient(ingredient) }

        return response.mapOnSuccess { items?.toMealModels().orEmpty() }
    }
}