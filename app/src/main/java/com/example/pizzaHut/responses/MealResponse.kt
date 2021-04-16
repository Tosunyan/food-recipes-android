package com.example.pizzaHut.responses

import com.example.pizzaHut.models.Meal
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MealResponse {

    @SerializedName("meals")
    @Expose
    val meals: List<Meal>? = null
}