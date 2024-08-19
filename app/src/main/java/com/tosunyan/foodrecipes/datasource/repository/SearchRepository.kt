package com.tosunyan.foodrecipes.datasource.repository

import com.tosunyan.foodrecipes.datasource.local.database.DatabaseProvider
import com.tosunyan.foodrecipes.datasource.local.database.MealDatabase
import com.tosunyan.foodrecipes.datasource.remote.api.Api
import com.tosunyan.foodrecipes.datasource.remote.api.ApiResponse
import com.tosunyan.foodrecipes.datasource.remote.data.ListDto
import com.tosunyan.foodrecipes.datasource.remote.data.MealDetailsDto
import com.tosunyan.foodrecipes.domain.mapper.toMealDetailsModels
import com.tosunyan.foodrecipes.domain.model.MealDetailsModel

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