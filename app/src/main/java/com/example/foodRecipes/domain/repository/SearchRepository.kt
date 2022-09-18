package com.example.foodRecipes.domain.repository

import com.example.foodRecipes.datasource.remote.api.Api
import com.example.foodRecipes.datasource.remote.api.ApiResponse
import com.example.foodRecipes.datasource.remote.api.makeApiCall
import com.example.foodRecipes.domain.mapper.toMealModel
import com.example.foodRecipes.domain.model.MealModel

class SearchRepository {

    suspend fun searchMeals(searchQuery: String): ApiResponse<List<MealModel>> {
        val response = makeApiCall {
            if (searchQuery.length == 1) {
                Api.client.searchMealByFirstLetter(searchQuery.first())
            } else {
                Api.client.searchMealByName(searchQuery)
            }
        }

        return when (response) {
            is ApiResponse.Success -> {
                val data = response.data.meals?.map { it.toMealModel() } ?: emptyList()
                ApiResponse.Success(data)
            }
            is ApiResponse.Failure -> {
                response
            }
        }
    }
}