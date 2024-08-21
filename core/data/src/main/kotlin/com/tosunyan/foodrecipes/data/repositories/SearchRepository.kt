package com.tosunyan.foodrecipes.data.repositories

import com.tosunyan.foodrecipes.data.mappers.toMealDetailsModels
import com.tosunyan.foodrecipes.database.DatabaseProvider
import com.tosunyan.foodrecipes.database.MealDatabase
import com.tosunyan.foodrecipes.model.MealDetailsModel
import com.tosunyan.foodrecipes.network.api.Api
import com.tosunyan.foodrecipes.network.api.ApiResponse
import com.tosunyan.foodrecipes.network.data.ListDto
import com.tosunyan.foodrecipes.network.data.MealDetailsDto

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