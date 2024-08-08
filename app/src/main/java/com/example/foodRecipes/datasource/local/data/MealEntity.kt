package com.example.foodRecipes.datasource.local.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meals")
data class MealEntity(
    @PrimaryKey
    val id: String,

    val name: String,
    val thumbnail: String,
    val category: String,
    val region: String,
    val instructions: String,

    @ColumnInfo("youtube_url")
    val youtubeUrl: String? = null,

    @ColumnInfo("source_url")
    val sourceUrl: String? = null,
)