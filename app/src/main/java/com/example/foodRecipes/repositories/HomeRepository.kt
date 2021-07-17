package com.example.foodRecipes.repositories

import com.example.foodRecipes.network.ApiService

class HomeRepository(private val apiService: ApiService) {

    suspend fun getRandomMeal() = apiService.getRandomMeal()

    suspend fun getCategories() = apiService.getCategories()

    suspend fun getAreas() = apiService.getAreas()
}