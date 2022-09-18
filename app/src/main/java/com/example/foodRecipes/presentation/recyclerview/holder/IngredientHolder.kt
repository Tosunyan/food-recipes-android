package com.example.foodRecipes.presentation.recyclerview.holder

import com.example.foodRecipes.databinding.ItemIngredientBinding
import com.example.foodRecipes.domain.model.IngredientModel

class IngredientHolder(
    private val binding: ItemIngredientBinding
) : SimpleViewHolder<IngredientModel>(binding.root) {

    override fun onBind(item: IngredientModel) = binding.apply {
        advancedIngredient.text = item.name
        advancedMeasure.text = item.quantity.trim()
    }
}