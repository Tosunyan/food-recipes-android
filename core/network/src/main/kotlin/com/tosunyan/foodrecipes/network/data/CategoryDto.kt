package com.tosunyan.foodrecipes.network.data

import kotlinx.serialization.Serializable

@Serializable
data class CategoryDto(
    val strCategory: String,
    val strCategoryThumb: String,
    val strCategoryDescription: String
)