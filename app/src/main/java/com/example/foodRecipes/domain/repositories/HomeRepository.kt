package com.example.foodRecipes.domain.repositories

import com.example.foodRecipes.data.remote.ApiService

class HomeRepository(private val apiService: ApiService) {

    suspend fun getRandomMeal() = apiService.getRandomMeal()

    suspend fun getCategories() = apiService.getCategories()

    suspend fun getAreas() = apiService.getAreas()
}