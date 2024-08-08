package com.example.foodRecipes.domain.mapper

import com.example.foodRecipes.datasource.local.data.IngredientEntity
import com.example.foodRecipes.datasource.remote.data.MealDetailsDto
import com.example.foodRecipes.domain.model.IngredientModel
import com.example.foodRecipes.domain.model.MealDetailsModel
import java.util.UUID

fun IngredientEntity.toIngredientModel(): IngredientModel {
    return IngredientModel(
        id = id,
        name = name,
        quantity = quantity
    )
}

fun MealDetailsModel.toIngredientEntities(): List<IngredientEntity> {
    return ingredients.map { it.toIngredientEntity(id) }
}

fun MealDetailsDto.toIngredientModels(): List<IngredientModel> {
    return buildSet {
        addIngredientIfValid(strIngredient1, strMeasure1)
        addIngredientIfValid(strIngredient2, strMeasure2)
        addIngredientIfValid(strIngredient3, strMeasure3)
        addIngredientIfValid(strIngredient4, strMeasure4)
        addIngredientIfValid(strIngredient5, strMeasure5)
        addIngredientIfValid(strIngredient6, strMeasure6)
        addIngredientIfValid(strIngredient7, strMeasure7)
        addIngredientIfValid(strIngredient8, strMeasure8)
        addIngredientIfValid(strIngredient9, strMeasure9)
        addIngredientIfValid(strIngredient10, strMeasure10)
        addIngredientIfValid(strIngredient11, strMeasure11)
        addIngredientIfValid(strIngredient12, strMeasure12)
        addIngredientIfValid(strIngredient13, strMeasure13)
        addIngredientIfValid(strIngredient14, strMeasure14)
        addIngredientIfValid(strIngredient15, strMeasure15)
        addIngredientIfValid(strIngredient16, strMeasure16)
        addIngredientIfValid(strIngredient17, strMeasure17)
        addIngredientIfValid(strIngredient18, strMeasure18)
        addIngredientIfValid(strIngredient19, strMeasure19)
        addIngredientIfValid(strIngredient20, strMeasure20)
    }.toList()
}

private fun IngredientModel.toIngredientEntity(mealId: String): IngredientEntity {
    return IngredientEntity(
        id = id,
        mealId = mealId,
        name = name,
        quantity = quantity,
    )
}

private fun MutableSet<IngredientModel>.addIngredientIfValid(ingredient: String?, measure: String?) {
    asValidIngredientOrNull(ingredient, measure)?.let(::add)
}

private fun asValidIngredientOrNull(name: String?, measure: String?): IngredientModel? {
    if (name.isNullOrBlank() || measure.isNullOrBlank()) return null
    return IngredientModel(
        id = UUID.randomUUID().toString(),
        name = name,
        quantity = measure
    )
}