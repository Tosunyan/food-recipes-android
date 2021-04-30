package com.example.foodRecipes.responses

import com.example.foodRecipes.models.Meal

data class MealResponse(
        val meals: List<Meal>
)