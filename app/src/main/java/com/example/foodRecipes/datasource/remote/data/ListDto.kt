package com.example.foodRecipes.datasource.remote.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ListDto<T>(
    @SerialName("meals")
    val items: List<T> = emptyList()
)