package com.example.foodRecipes.domain.mapper

import com.example.foodRecipes.datasource.local.data.IngredientEntity
import com.example.foodRecipes.datasource.local.data.MealWithIngredients
import com.example.foodRecipes.domain.model.MealDetailsModel

fun List<MealWithIngredients>.toMealDetailsList(): List<MealDetailsModel> {
    return map(MealWithIngredients::toMealDetailsModel)
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