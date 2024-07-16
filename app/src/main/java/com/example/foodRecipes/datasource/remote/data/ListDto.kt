package com.example.foodRecipes.datasource.remote.data

import com.google.gson.annotations.SerializedName

data class ListDto<T>(
    // TODO Set default to emptyList when
    //  migrated to KotlinX Serialization
    @SerializedName("meals")
    val items: List<T>? = null
)