package com.example.foodRecipes.datasource.remote.data

import kotlinx.serialization.Serializable

@Serializable
data class MealDto(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String,
)