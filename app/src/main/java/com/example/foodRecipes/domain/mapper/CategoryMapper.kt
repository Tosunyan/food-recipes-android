package com.example.foodRecipes.domain.mapper

import com.example.foodRecipes.datasource.remote.data.CategoryDto
import com.example.foodRecipes.domain.model.CategoryModel

fun CategoryDto.toCategoryModel(): CategoryModel {
    return CategoryModel(
        name = strCategory,
        thumbnail = strCategoryThumb,
        description = strCategoryDescription
    )
}