package com.example.foodRecipes.datasource.local.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ingredients")
data class IngredientEntity(
    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "meal_id")
    val mealId: String,

    val name: String,
    val quantity: String,
)