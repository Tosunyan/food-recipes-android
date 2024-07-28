package com.example.foodRecipes.datasource.repository

import com.example.foodRecipes.datasource.remote.api.Api
import com.example.foodRecipes.datasource.remote.api.ApiResponse
import com.example.foodRecipes.datasource.remote.api.makeApiCall
import com.example.foodRecipes.datasource.remote.api.mapOnSuccess
import com.example.foodRecipes.datasource.remote.data.ListDto
import com.example.foodRecipes.datasource.remote.data.MealDetailsDto
import com.example.foodRecipes.domain.mapper.toMealDetailsModel
import com.example.foodRecipes.domain.model.MealDetailsModel

class SearchRepository {

    suspend fun searchMeals(searchQuery: String): ApiResponse<List<MealDetailsModel>> {
        val response = if (searchQuery.length == 1) {
            searchByFirstLetter(searchQuery.first())
        } else {
            searchByName(searchQuery)
        }

        return response.mapOnSuccess {
            items?.map(MealDetailsDto::toMealDetailsModel).orEmpty()
        }
    }

    private suspend fun searchByFirstLetter(letter: Char): ApiResponse<ListDto<MealDetailsDto>> {
        return makeApiCall { Api.client.searchMealByFirstLetter(letter) }
    }

    private suspend fun searchByName(name: String): ApiResponse<ListDto<MealDetailsDto>> {
        return makeApiCall { Api.client.searchMealByName(name) }
    }
}