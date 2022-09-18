package com.example.foodRecipes.domain.repository

import com.example.foodRecipes.datasource.remote.api.Api
import com.example.foodRecipes.datasource.remote.api.makeApiCall

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