package com.example.foodRecipes.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MealModel(
    val id: String,
    val name: String,
    val category: String? = null,
    val region: String? = null,
    val instructions: String? = null,
    val thumbnail: String? = null,
    val youtubeUrl: String? = null,
    val sourceUrl: String? = null,
    val ingredients: List<IngredientModel> = emptyList(),
) : Parcelable