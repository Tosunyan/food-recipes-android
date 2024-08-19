package com.tosunyan.foodrecipes.datasource.remote.data

import kotlinx.serialization.Serializable

@Serializable
data class CategoryDto(
    val strCategory: String,
    val strCategoryThumb: String,
    val strCategoryDescription: String
)