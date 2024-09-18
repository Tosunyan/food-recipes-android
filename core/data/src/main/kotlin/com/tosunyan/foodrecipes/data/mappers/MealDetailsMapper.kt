package com.tosunyan.foodrecipes.data.mappers

import com.tosunyan.foodrecipes.database.model.MealEntity
import com.tosunyan.foodrecipes.model.MealDetailsModel
import com.tosunyan.foodrecipes.network.data.ListDto
import com.tosunyan.foodrecipes.network.data.MealDetailsDto

fun ListDto<MealDetailsDto>.toMealDetailsModel(
    savedIds: List<String> = emptyList()
): MealDetailsModel {
    return items.first().toMealDetailsModel(savedIds)
}

fun ListDto<MealDetailsDto>.toMealDetailsModels(
    savedIds: List<String> = emptyList()
): List<MealDetailsModel> {
    return items.map { it.toMealDetailsModel(savedIds) }
}

fun MealDetailsDto.toMealDetailsModel(
    savedIds: List<String> = emptyList()
): MealDetailsModel {
    return MealDetailsModel(
        id = idMeal,
        name = strMeal,
        category = strCategory ?: "",
        region = strArea ?: "",
        instructions = strInstructions.trim(),
        thumbnail = strMealThumb,
        youtubeUrl = strYoutube.takeIf { !it.isNullOrBlank() },
        sourceUrl = strSource.takeIf { !it.isNullOrBlank() },
        ingredients = toIngredientModels(),
        isSaved = idMeal in savedIds
    )
}

fun MealDetailsModel.toMealEntity(): MealEntity {
    return MealEntity(
        id = id,
        name = name,
        thumbnail = thumbnail,
        category = category,
        region = region,
        instructions = instructions,
        youtubeUrl = youtubeUrl,
        sourceUrl = sourceUrl
    )
}