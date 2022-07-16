package com.example.foodRecipes.domain.repositories

import com.example.foodRecipes.data.remote.Api
import com.example.foodRecipes.data.remote.makeApiCall

class SearchRepository {

    suspend fun searchByFirstLetter(letter: Char) = makeApiCall {
        Api.client.searchMealByFirstLetter(letter)
    }

    suspend fun searchByName(name: String?) = makeApiCall {
        Api.client.searchMealByName(name)
    }
}