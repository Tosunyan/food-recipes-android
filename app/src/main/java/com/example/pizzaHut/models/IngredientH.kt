package com.example.pizzaHut.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class IngredientH(
        @SerializedName("idIngredient")
        @Expose
        val ingredientID: String?,

        @SerializedName("strIngredient")
        @Expose
        val ingredientName: String?,

        @SerializedName("strDescription")
        @Expose
        val ingredientDescription: String?
)