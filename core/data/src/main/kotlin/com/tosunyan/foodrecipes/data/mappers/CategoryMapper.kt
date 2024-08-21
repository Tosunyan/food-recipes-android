package com.tosunyan.foodrecipes.data.mappers

import com.tosunyan.foodrecipes.model.CategoryModel
import com.tosunyan.foodrecipes.network.data.CategoryDto

fun CategoryDto.toCategoryModel(): CategoryModel {
    return CategoryModel(
        name = strCategory,
        thumbnail = strCategoryThumb,
        description = strCategoryDescription
    )
}