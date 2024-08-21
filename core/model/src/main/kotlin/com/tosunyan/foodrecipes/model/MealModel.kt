package com.tosunyan.foodrecipes.model

import java.io.Serializable

data class MealModel(
    val id: String,
    val name: String = "",
    val thumbnail: String = "",

    val isSaved: Boolean = false,
) : Serializable