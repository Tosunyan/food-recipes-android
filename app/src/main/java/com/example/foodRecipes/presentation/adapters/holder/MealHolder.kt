package com.example.foodRecipes.presentation.adapters.holder

import coil.load
import com.example.foodRecipes.domain.model.MealModel
import com.example.foodRecipes.databinding.ItemMealBinding

class MealHolder(
    private val binding: ItemMealBinding,
) : SimpleViewHolder<MealModel>(binding.root) {

    override fun onBind(item: MealModel) = with(binding) {
        mealName.text = item.name
        mealImage.load(item.image)
    }
}