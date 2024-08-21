package com.tosunyan.foodrecipes.model

import java.io.Serializable

data class CategoryModel(
    val name: String,
    val thumbnail: String,
    val description: String
): Serializable