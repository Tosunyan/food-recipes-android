package com.tosunyan.foodrecipes.data.repositories

import com.tosunyan.foodrecipes.common.coroutines.DispatcherProvider
import com.tosunyan.foodrecipes.common.coroutines.DispatcherScope
import com.tosunyan.foodrecipes.data.mappers.toMealDetailsModels
import com.tosunyan.foodrecipes.database.MealDatabase
import com.tosunyan.foodrecipes.model.MealDetailsModel
import com.tosunyan.foodrecipes.network.api.ApiService
import com.tosunyan.foodrecipes.network.data.ListDto
import com.tosunyan.foodrecipes.network.data.MealDetailsDto

class SearchRepository(
    override val dispatcherProvider: DispatcherProvider,
    private val apiService: ApiService,
    private val database: MealDatabase,
): DispatcherScope {

    init {
        println("${this::class.simpleName}.apiService: ${apiService::class.simpleName}")
    }

    suspend fun searchMeals(searchQuery: String): List<MealDetailsModel> {
        val apiCall = if (searchQuery.length == 1) {
            searchByFirstLetter(searchQuery.first())
        } else {
            searchByName(searchQuery)
        }

        return getMealsWithSavedStatus(
            mealDao = database.mealDao,
            apiCall = apiCall,
            mapper = ListDto<MealDetailsDto>::toMealDetailsModels
        ).orEmpty()
    }

    private fun searchByFirstLetter(letter: Char) =
        suspend { apiService.searchMealByFirstLetter(letter) }

    private fun searchByName(name: String) =
        suspend { apiService.searchMealByName(name) }
}