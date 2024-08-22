package com.tosunyan.foodrecipes.model

import java.io.Serializable

data class MealDetailsModel(
    override val id: String = "",
    override val isSaved: Boolean = false,
    val name: String = "",
    val thumbnail: String = "",
    val category: String = "",
    val region: String = "",
    val instructions: String = "",
    val youtubeUrl: String? = null,
    val sourceUrl: String? = null,
    val ingredients: List<IngredientModel> = emptyList(),
) : SaveableMeal, Serializable