package com.example.foodRecipes.domain.repositories

import com.example.foodRecipes.data.remote.api.Api
import com.example.foodRecipes.data.remote.api.makeApiCall

class MealRepository {

    suspend fun filterMealsByCategory(category: String?) = makeApiCall {
        Api.client.filterMealsByCategory(category)
    }

    suspend fun filterMealsByArea(area: String?) = makeApiCall {
        Api.client.filterMealsByArea(area)
    }

    suspend fun filterMealsByIngredient(ingredient: String?) = makeApiCall {
        Api.client.filterMealsByIngredient(ingredient)
    }
}