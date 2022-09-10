package com.example.foodRecipes.domain.mapper

import com.example.foodRecipes.data.remote.data.MealDetailsDto
import com.example.foodRecipes.data.remote.data.MealDto
import com.example.foodRecipes.domain.model.IngredientModel
import com.example.foodRecipes.domain.model.MealModel

fun MealDto.toMealModel() = MealModel(
    name = strMeal,
    category = null,
    region = null,
    instructions = null,
    image = null,
    youtubeUrl = null,
    sourceUrl = null,
    ingredients = null,
)

fun MealDetailsDto.toMealModel() = MealModel(
    name = strMeal,
    category = strCategory ?: "",
    region = strArea ?: "",
    instructions = strInstructions,
    image = strMealThumb,
    youtubeUrl = strYoutube,
    sourceUrl = strSource,
    ingredients = buildList {
        if (isValidIngredient(strIngredient1, strMeasure1)) add(IngredientModel(strIngredient1!!, strMeasure1!!))
        if (isValidIngredient(strIngredient2, strMeasure2)) add(IngredientModel(strIngredient2!!, strMeasure2!!))
        if (isValidIngredient(strIngredient3, strMeasure3)) add(IngredientModel(strIngredient3!!, strMeasure3!!))
        if (isValidIngredient(strIngredient4, strMeasure4)) add(IngredientModel(strIngredient4!!, strMeasure4!!))
        if (isValidIngredient(strIngredient5, strMeasure5)) add(IngredientModel(strIngredient5!!, strMeasure5!!))
        if (isValidIngredient(strIngredient6, strMeasure6)) add(IngredientModel(strIngredient6!!, strMeasure6!!))
        if (isValidIngredient(strIngredient7, strMeasure7)) add(IngredientModel(strIngredient7!!, strMeasure7!!))
        if (isValidIngredient(strIngredient8, strMeasure8)) add(IngredientModel(strIngredient8!!, strMeasure8!!))
        if (isValidIngredient(strIngredient9, strMeasure9)) add(IngredientModel(strIngredient9!!, strMeasure9!!))
        if (isValidIngredient(strIngredient10, strMeasure10)) add(IngredientModel(strIngredient10!!, strMeasure10!!))
        if (isValidIngredient(strIngredient11, strMeasure11)) add(IngredientModel(strIngredient11!!, strMeasure11!!))
        if (isValidIngredient(strIngredient12, strMeasure12)) add(IngredientModel(strIngredient12!!, strMeasure12!!))
        if (isValidIngredient(strIngredient13, strMeasure13)) add(IngredientModel(strIngredient13!!, strMeasure13!!))
        if (isValidIngredient(strIngredient14, strMeasure14)) add(IngredientModel(strIngredient14!!, strMeasure14!!))
        if (isValidIngredient(strIngredient15, strMeasure15)) add(IngredientModel(strIngredient15!!, strMeasure15!!))
        if (isValidIngredient(strIngredient16, strMeasure16)) add(IngredientModel(strIngredient16!!, strMeasure16!!))
        if (isValidIngredient(strIngredient17, strMeasure17)) add(IngredientModel(strIngredient17!!, strMeasure17!!))
        if (isValidIngredient(strIngredient18, strMeasure18)) add(IngredientModel(strIngredient18!!, strMeasure18!!))
        if (isValidIngredient(strIngredient19, strMeasure19)) add(IngredientModel(strIngredient19!!, strMeasure19!!))
        if (isValidIngredient(strIngredient20, strMeasure20)) add(IngredientModel(strIngredient20!!, strMeasure20!!))
    }
)

private fun isValidIngredient(name: String?, measure: String?) =
    name != null && name.isNotBlank() && measure != null && measure.isNotBlank()