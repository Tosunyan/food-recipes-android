package com.tosunyan.foodrecipes.model

import java.io.Serializable

data class IngredientModel(
    val id: String,
    val name: String,
    val quantity: String
): Serializable