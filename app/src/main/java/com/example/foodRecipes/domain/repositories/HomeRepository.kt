package com.example.foodRecipes.domain.repositories

import com.example.foodRecipes.data.remote.Api
import com.example.foodRecipes.data.remote.makeApiCall

class HomeRepository {

    suspend fun getRandomMeal() = makeApiCall {
        Api.client.getRandomMeal()
    }

    suspend fun getCategories() = makeApiCall {
        Api.client.getCategories()
    }

    suspend fun getAreas() = makeApiCall {
        Api.client.getAreas()
    }
}