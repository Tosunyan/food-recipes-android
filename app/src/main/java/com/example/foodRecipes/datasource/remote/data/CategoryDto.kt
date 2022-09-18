package com.example.foodRecipes.datasource.remote.data

import com.example.foodRecipes.domain.model.Category

data class CategoriesDto(
    val categories: List<Category>
)

data class CategoryDto(
    val strCategory: String,
    val strCategoryThumb: String,
    val strCategoryDescription: String
)