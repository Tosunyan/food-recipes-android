package com.tosunyan.foodrecipes.domain.usecase

import com.tosunyan.foodrecipes.datasource.remote.api.ApiResponse
import com.tosunyan.foodrecipes.domain.model.CategoryModel
import com.tosunyan.foodrecipes.datasource.repository.HomeRepository

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