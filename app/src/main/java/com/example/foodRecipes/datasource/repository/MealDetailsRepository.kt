package com.example.foodRecipes.datasource.repository

import com.example.foodRecipes.datasource.remote.api.Api
import com.example.foodRecipes.datasource.remote.api.ApiResponse
import com.example.foodRecipes.datasource.remote.data.MealDetailsDto
import com.example.foodRecipes.datasource.remote.api.makeApiCall
import com.example.foodRecipes.datasource.remote.api.mapOnSuccess

class MealDetailsRepository {

    suspend fun getMealDetails(id: String): ApiResponse<MealDetailsDto> {
        val response = makeApiCall { Api.client.getMealDetails(id) }

        return response.mapOnSuccess { meals.first() }
    }
}