package com.example.foodRecipes.datasource.repository

import com.example.foodRecipes.datasource.local.database.DatabaseProvider
import com.example.foodRecipes.datasource.local.database.MealDatabase
import com.example.foodRecipes.datasource.remote.api.Api
import com.example.foodRecipes.datasource.remote.api.ApiResponse
import com.example.foodRecipes.datasource.remote.data.ListDto
import com.example.foodRecipes.datasource.remote.data.MealDetailsDto
import com.example.foodRecipes.domain.mapper.toMealDetailsModels
import com.example.foodRecipes.domain.model.MealDetailsModel

class SearchRepository(
    private val database: MealDatabase = DatabaseProvider.instance,
) {

    suspend fun searchMeals(searchQuery: String): ApiResponse<List<MealDetailsModel>> {
        val apiCall = if (searchQuery.length == 1) {
            searchByFirstLetter(searchQuery.first())
        } else {
            searchByName(searchQuery)
        }

        return getMealsWithSavedStatus(
            mealDao = database.mealDao,
            apiCall = apiCall,
            mapper = ListDto<MealDetailsDto>::toMealDetailsModels
        )
    }

    private suspend fun searchByFirstLetter(letter: Char) =
        suspend { Api.client.searchMealByFirstLetter(letter) }

    private suspend fun searchByName(name: String) =
        suspend { Api.client.searchMealByName(name) }
}