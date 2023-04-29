package com.example.foodRecipes.domain.usecase

import com.example.foodRecipes.datasource.remote.api.ApiResponse
import com.example.foodRecipes.datasource.remote.data.RegionDto
import com.example.foodRecipes.domain.model.CategoryModel
import com.example.foodRecipes.domain.repository.HomeRepository

object GetRegions {

    private val homeRepository = HomeRepository()
    private var regionsResponse: ApiResponse<List<RegionDto>>? = null

    suspend fun execute(): ApiResponse<List<RegionDto>> {
        if (regionsResponse == null || regionsResponse is ApiResponse.Failure) {
            regionsResponse = when (val response = homeRepository.getAreas()) {
                is ApiResponse.Success -> ApiResponse.Success(response.data.meals)
                is ApiResponse.Failure -> response
            }
        }

        return regionsResponse!!
    }
}