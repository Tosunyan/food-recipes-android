package com.tosunyan.foodrecipes.domain.mapper

import com.tosunyan.foodrecipes.datasource.local.data.IngredientEntity
import com.tosunyan.foodrecipes.datasource.local.data.MealWithIngredients
import com.tosunyan.foodrecipes.domain.model.MealDetailsModel

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