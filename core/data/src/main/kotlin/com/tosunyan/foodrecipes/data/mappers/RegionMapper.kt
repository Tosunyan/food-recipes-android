package com.tosunyan.foodrecipes.data.mappers

import com.tosunyan.foodrecipes.model.RegionModel
import com.tosunyan.foodrecipes.network.data.RegionDto

fun List<RegionDto>.toRegionModels(): List<RegionModel> {
    return map(RegionDto::toModel).distinctBy(RegionModel::name)
}

fun RegionDto.toModel(): RegionModel {
    return RegionModel(
        name = strArea
    )
}