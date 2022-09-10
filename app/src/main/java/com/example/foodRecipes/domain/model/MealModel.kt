package com.example.foodRecipes.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class MealModel(
        val name: String,
        val category: String?,
        val region: String?,
        val instructions: String?,
        val image: String?,
        val youtubeUrl: String?,
        val sourceUrl: String?,

        val ingredients: @RawValue List<IngredientModel>?,
) : Parcelable