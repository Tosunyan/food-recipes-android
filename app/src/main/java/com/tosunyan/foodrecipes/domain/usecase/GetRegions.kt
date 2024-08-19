package com.tosunyan.foodrecipes.domain.usecase

import com.tosunyan.foodrecipes.datasource.remote.api.ApiResponse
import com.tosunyan.foodrecipes.datasource.repository.HomeRepository
import com.tosunyan.foodrecipes.domain.model.RegionModel

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