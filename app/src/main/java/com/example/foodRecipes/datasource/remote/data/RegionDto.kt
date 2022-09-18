package com.example.foodRecipes.datasource.remote.data

data class RegionsDto(
    val meals: List<RegionDto>
)

data class RegionDto(
    val strArea: String
)