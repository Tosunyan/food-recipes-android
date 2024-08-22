package com.tosunyan.foodrecipes.model

sealed interface SaveableMeal {

    val id: String
    val isSaved: Boolean
}