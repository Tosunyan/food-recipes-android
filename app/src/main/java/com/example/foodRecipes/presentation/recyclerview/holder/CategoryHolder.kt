package com.example.foodRecipes.presentation.recyclerview.holder

import coil.load
import com.example.foodRecipes.domain.model.CategoryModel
import com.example.foodRecipes.databinding.ItemCategoryBinding

class CategoryHolder(
    private val binding: ItemCategoryBinding
) : SimpleViewHolder<CategoryModel>(binding.root) {

    override fun onBind(item: CategoryModel) = with(binding) {
        categoryName.text = item.name
        categoryImage.load(item.thumbnail)
    }
}