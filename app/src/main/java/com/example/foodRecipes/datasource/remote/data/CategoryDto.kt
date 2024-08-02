package com.example.foodRecipes.datasource.remote.data

import kotlinx.serialization.Serializable

@Serializable
data class CategoriesDto(
    val categories: List<CategoryDto>
)

@Serializable
data class CategoryDto(
    val strCategory: String,
    val strCategoryThumb: String,
    val strCategoryDescription: String
)