package com.tosunyan.foodrecipes.data.mappers

import com.tosunyan.foodrecipes.model.MealDetailsModel
import com.tosunyan.foodrecipes.model.MealModel
import com.tosunyan.foodrecipes.network.data.ListDto
import com.tosunyan.foodrecipes.network.data.MealDto

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