package com.example.foodRecipes.presentation.recyclerview.holder

import com.example.foodRecipes.databinding.ItemAreaBinding

class RegionHolder(
    private val binding: ItemAreaBinding
) : SimpleViewHolder<String>(binding.root) {

    override fun onBind(item: String) = with(binding) {
        root.text = item
    }
}