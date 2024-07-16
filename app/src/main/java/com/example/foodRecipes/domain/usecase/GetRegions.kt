package com.example.foodRecipes.domain.usecase

import com.example.foodRecipes.datasource.remote.api.ApiResponse
import com.example.foodRecipes.datasource.repository.HomeRepository
import com.example.foodRecipes.domain.model.RegionModel

object GetRegions {

    private val homeRepository = HomeRepository()
    private var regionsResponse: ApiResponse<List<RegionModel>>? = null

    suspend fun execute(): ApiResponse<List<RegionModel>> {
        if (regionsResponse == null || regionsResponse is ApiResponse.Failure) {
            regionsResponse = homeRepository.getRegions()
        }

        return regionsResponse!!
    }
}