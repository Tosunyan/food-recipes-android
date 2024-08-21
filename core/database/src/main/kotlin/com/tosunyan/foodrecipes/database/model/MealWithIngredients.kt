package com.tosunyan.foodrecipes.database.model

import androidx.room.Embedded
import androidx.room.Relation

data class MealWithIngredients(
    @Embedded
    val meal: MealEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "meal_id"
    )
    val ingredients: List<IngredientEntity>,
)