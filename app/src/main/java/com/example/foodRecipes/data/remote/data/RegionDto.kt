package com.example.foodRecipes.data.remote.data

data class RegionsDto(
    val meals: List<RegionDto>
)

data class RegionDto(
    val strArea: String
)