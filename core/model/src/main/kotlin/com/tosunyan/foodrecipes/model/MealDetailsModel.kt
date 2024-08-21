package com.tosunyan.foodrecipes.model

import java.io.Serializable

data class MealDetailsModel(
    val id: String = "",
    val name: String = "",
    val thumbnail: String = "",
    val category: String = "",
    val region: String = "",
    val instructions: String = "",
    val youtubeUrl: String? = null,
    val sourceUrl: String? = null,
    val ingredients: List<IngredientModel> = emptyList(),

    val isSaved: Boolean = false,
) : Serializable