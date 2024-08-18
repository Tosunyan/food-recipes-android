package com.example.foodRecipes.domain.mapper

import com.example.foodRecipes.datasource.remote.data.ListDto
import com.example.foodRecipes.datasource.remote.data.MealDto
import com.example.foodRecipes.domain.model.MealDetailsModel
import com.example.foodRecipes.domain.model.MealModel

fun ListDto<MealDto>.toMealModels(
    savedIds: List<String> = emptyList()
): List<MealModel> {
    return items.map { it.toMealModel(savedIds) }
}

fun MealDto.toMealModel(savedIds: List<String>): MealModel {
    return MealModel(
        id = idMeal,
        name = strMeal,
        thumbnail = strMealThumb,

        isSaved = idMeal in savedIds
    )
}

fun MealModel.toMealDetailsModel(): MealDetailsModel {
    return MealDetailsModel(
        id = id,
        name = name,
        thumbnail = thumbnail,
        isSaved = isSaved
    )
}