package com.tosunyan.foodrecipes.data.mappers

import com.tosunyan.foodrecipes.database.model.IngredientEntity
import com.tosunyan.foodrecipes.database.model.MealWithIngredients
import com.tosunyan.foodrecipes.model.MealDetailsModel

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