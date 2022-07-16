package com.example.foodRecipes.presentation.adapters.holder

import coil.load
import com.example.foodRecipes.data.models.Meal
import com.example.foodRecipes.databinding.ItemMealBinding
import com.example.foodRecipes.presentation.adapters.holder.SimpleViewHolder

class MealHolder(
    private val binding: ItemMealBinding,
) : SimpleViewHolder<Meal>(binding.root) {

    override fun onBind(item: Meal) = with(binding) {
        mealName.text = item.strMeal
        mealImage.load(item.strMealThumb)
    }
}