package com.example.pizzaHut.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Category(
        @SerializedName("strCategory")
        @Expose
        val strCategory: String? = null,

        @SerializedName("strCategoryThumb")
        @Expose
        val strCategoryThumb: String? = null,

        @SerializedName("strCategoryDescription")
        @Expose
        val strCategoryDescription: String? = null
)