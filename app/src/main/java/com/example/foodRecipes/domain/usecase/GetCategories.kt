package com.example.foodRecipes.domain.usecase

import com.example.foodRecipes.datasource.remote.api.ApiResponse
import com.example.foodRecipes.domain.model.CategoryModel
import com.example.foodRecipes.datasource.repository.HomeRepository

object GetCategories {

    private val homeRepository = HomeRepository()
    private var categoriesResponse: ApiResponse<List<CategoryModel>>? = null

    suspend fun execute(): ApiResponse<List<CategoryModel>> {
        if (categoriesResponse == null || categoriesResponse is ApiResponse.Failure) {
            categoriesResponse = homeRepository.getCategories()
        }

        return categoriesResponse!!
    }
}