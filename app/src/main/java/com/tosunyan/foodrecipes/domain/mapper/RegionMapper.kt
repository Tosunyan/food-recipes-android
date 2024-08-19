package com.tosunyan.foodrecipes.domain.mapper

import com.tosunyan.foodrecipes.datasource.remote.data.RegionDto
import com.tosunyan.foodrecipes.domain.model.RegionModel

fun List<RegionDto>.toRegionModels(): List<RegionModel> {
    return map(RegionDto::toModel)
}

fun RegionDto.toModel(): RegionModel {
    return RegionModel(
        name = strArea
    )
}