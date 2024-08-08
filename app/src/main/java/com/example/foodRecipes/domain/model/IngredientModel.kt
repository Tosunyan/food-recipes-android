package com.example.foodRecipes.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class IngredientModel(
    val id: String,
    val name: String,
    val quantity: String
): Parcelable