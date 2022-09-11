package com.example.foodRecipes.domain.repositories

import com.example.foodRecipes.data.remote.Api
import com.example.foodRecipes.data.remote.ApiResponse
import com.example.foodRecipes.data.remote.data.MealDetailsDto
import com.example.foodRecipes.data.remote.makeApiCall

class DescriptionRepository {

    suspend fun getMealDetails(id: String): ApiResponse<MealDetailsDto> {
        return when (val response = makeApiCall { Api.client.getMealDetails(id) }) {
            is ApiResponse.Success -> ApiResponse.Success(response.data.meals.first())
            is ApiResponse.Failure -> response
        }
    }
}