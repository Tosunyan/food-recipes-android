package com.example.foodRecipes.datasource.remote.data

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class ListDto<T>(
    @JsonNames("meals", "categories")
    val items: List<T> = emptyList()
)