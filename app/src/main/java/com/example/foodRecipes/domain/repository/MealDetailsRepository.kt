package com.example.foodRecipes.domain.repository

import com.example.foodRecipes.datasource.remote.api.Api
import com.example.foodRecipes.datasource.remote.api.ApiResponse
import com.example.foodRecipes.datasource.remote.data.MealDetailsDto
import com.example.foodRecipes.datasource.remote.api.makeApiCall

class MealDetailsRepository {

    suspend fun getMealDetails(id: String): ApiResponse<MealDetailsDto> {
        return when (val response = makeApiCall { Api.client.getMealDetails(id) }) {
            is ApiResponse.Success -> ApiResponse.Success(response.data.meals.first())
            is ApiResponse.Failure -> response
        }
    }
}