package com.example.foodRecipes.presentation.adapters

import com.example.foodRecipes.databinding.ItemAreaBinding
import com.example.foodRecipes.presentation.adapters.holder.SimpleViewHolder

class RegionHolder(
    private val binding: ItemAreaBinding
) : SimpleViewHolder<String>(binding.root) {

    override fun onBind(item: String) = with(binding) {
        root.text = item
    }
}