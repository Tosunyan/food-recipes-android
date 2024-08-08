package com.example.foodRecipes.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MealModel(
    val id: String,
    val name: String = "",
    val thumbnail: String = "",

    val isSaved: Boolean = false,
) : Parcelable