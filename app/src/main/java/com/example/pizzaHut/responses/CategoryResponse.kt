package com.example.pizzaHut.responses

import com.example.pizzaHut.models.Category
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CategoryResponse {

    @SerializedName("categories")
    @Expose
    val categories: List<Category>? = null
}