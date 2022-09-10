package com.example.foodRecipes.data.local.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MealEntity(
    val name: String,
    val category: String,
    val region: String,
    val instructions: String,
    val image: String,
    val youtubeUrl: String?,
    val sourceUrl: String?,

//    val ingredients: List<IngredientModel>, Todo: Will be fixed later
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}