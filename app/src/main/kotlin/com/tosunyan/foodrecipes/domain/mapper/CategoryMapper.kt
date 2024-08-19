package com.tosunyan.foodrecipes.domain.mapper

import com.tosunyan.foodrecipes.datasource.remote.data.CategoryDto
import com.tosunyan.foodrecipes.domain.model.CategoryModel

fun CategoryDto.toCategoryModel(): CategoryModel {
    return CategoryModel(
        name = strCategory,
        thumbnail = strCategoryThumb,
        description = strCategoryDescription
    )
}