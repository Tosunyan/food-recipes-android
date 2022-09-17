package com.example.foodRecipes.domain.repositories

import com.example.foodRecipes.data.remote.Api
import com.example.foodRecipes.data.remote.ApiResponse
import com.example.foodRecipes.data.remote.makeApiCall
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