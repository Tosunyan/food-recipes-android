package com.example.foodRecipes.domain.mapper

import com.example.foodRecipes.datasource.local.data.IngredientEntity
import com.example.foodRecipes.datasource.local.data.MealEntity
import com.example.foodRecipes.datasource.local.data.MealWithIngredients
import com.example.foodRecipes.datasource.remote.data.ListDto
import com.example.foodRecipes.datasource.remote.data.MealDetailsDto
import com.example.foodRecipes.datasource.remote.data.MealDto
import com.example.foodRecipes.domain.model.MealDetailsModel
import com.example.foodRecipes.domain.model.MealModel

fun ListDto<MealDto>.toMealModels(
    savedIds: List<String> = emptyList()
): List<MealModel> {
    return items.map { it.toMealModel(savedIds) }
}

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

fun List<MealWithIngredients>.toMealDetailsList(): List<MealDetailsModel> {
    return map(MealWithIngredients::toMealDetailsModel)
}

fun MealDto.toMealModel(savedIds: List<String>): MealModel {
    return MealModel(
        id = idMeal,
        name = strMeal,
        thumbnail = strMealThumb,

        isSaved = idMeal in savedIds
    )
}

fun MealDetailsDto.toMealDetailsModel(savedIds: List<String>): MealDetailsModel {
    return MealDetailsModel(
        id = idMeal,
        name = strMeal,
        category = strCategory ?: "",
        region = strArea ?: "",
        instructions = strInstructions,
        thumbnail = strMealThumb,
        youtubeUrl = strYoutube.takeIf { !it.isNullOrBlank() },
        sourceUrl = strSource.takeIf { !it.isNullOrBlank() },
        ingredients = toIngredientModels(),
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

fun MealWithIngredients.toMealDetailsModel(): MealDetailsModel {
    return MealDetailsModel(
        id = meal.id,
        name = meal.name,
        thumbnail = meal.thumbnail,
        category = meal.category,
        region = meal.region,
        instructions = meal.instructions,
        youtubeUrl = meal.youtubeUrl,
        sourceUrl = meal.sourceUrl,
        ingredients = ingredients.map(IngredientEntity::toIngredientModel),
        isSaved = true
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