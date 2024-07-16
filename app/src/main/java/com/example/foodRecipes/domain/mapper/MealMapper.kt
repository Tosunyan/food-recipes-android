package com.example.foodRecipes.domain.mapper

import com.example.foodRecipes.datasource.remote.data.MealDetailsDto
import com.example.foodRecipes.datasource.remote.data.MealDto
import com.example.foodRecipes.domain.model.MealModel

fun List<MealDto>.toMealModels(): List<MealModel> {
    return map(MealDto::toMealModel)
}

fun MealDto.toMealModel() = MealModel(
    id = idMeal,
    name = strMeal,
    thumbnail = strMealThumb,
)

fun MealDetailsDto.toMealModel() = MealModel(
    id = idMeal,
    name = strMeal,
    category = strCategory ?: "",
    region = strArea ?: "",
    instructions = strInstructions,
    thumbnail = strMealThumb,
    youtubeUrl = strYoutube,
    sourceUrl = strSource,
    ingredients = toIngredientModels(),
)