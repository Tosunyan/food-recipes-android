package com.example.foodRecipes.domain.repositories

import com.example.foodRecipes.data.remote.Api
import com.example.foodRecipes.data.remote.makeApiCall

class DescriptionRepository {

    suspend fun getMealDetails(id: String) = makeApiCall {
        Api.client.getMealDetails(id)
    }
}