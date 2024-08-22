package com.tosunyan.foodrecipes.model

import java.io.Serializable

data class MealModel(
    override val id: String,
    override val isSaved: Boolean = false,
    val name: String = "",
    val thumbnail: String = "",
) : SaveableMeal, Serializable