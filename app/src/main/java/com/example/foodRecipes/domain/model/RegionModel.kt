package com.example.foodRecipes.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RegionModel(
    val name: String,
): Parcelable