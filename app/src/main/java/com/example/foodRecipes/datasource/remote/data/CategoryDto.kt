package com.example.foodRecipes.datasource.remote.data

data class CategoriesDto(
    val categories: List<CategoryDto>
)

data class CategoryDto(
    val strCategory: String,
    val strCategoryThumb: String,
    val strCategoryDescription: String
)