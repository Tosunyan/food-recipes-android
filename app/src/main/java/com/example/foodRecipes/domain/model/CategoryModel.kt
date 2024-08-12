package com.example.foodRecipes.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryModel(
    val name: String,
    val thumbnail: String,
    val description: String
): Parcelable