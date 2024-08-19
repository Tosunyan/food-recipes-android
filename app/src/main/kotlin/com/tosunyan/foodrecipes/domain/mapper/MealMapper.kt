package com.tosunyan.foodrecipes.domain.mapper

import com.tosunyan.foodrecipes.datasource.remote.data.ListDto
import com.tosunyan.foodrecipes.datasource.remote.data.MealDto
import com.tosunyan.foodrecipes.domain.model.MealDetailsModel
import com.tosunyan.foodrecipes.domain.model.MealModel

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