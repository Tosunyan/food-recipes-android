package com.example.foodRecipes.domain.mapper

import com.example.foodRecipes.datasource.remote.data.RegionDto
import com.example.foodRecipes.domain.model.RegionModel

fun List<RegionDto>.toRegionModels(): List<RegionModel> {
    return map(RegionDto::toModel)
}

fun RegionDto.toModel(): RegionModel {
    return RegionModel(
        name = strArea
    )
}