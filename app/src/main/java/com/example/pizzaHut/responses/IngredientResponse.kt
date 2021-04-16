package com.example.pizzaHut.responses

import com.example.pizzaHut.models.IngredientH
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class IngredientResponse {

    @SerializedName("meals")
    @Expose
    val ingredients: List<IngredientH>? = null
}