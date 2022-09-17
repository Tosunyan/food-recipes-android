package com.example.foodRecipes.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodRecipes.presentation.adapters.IngredientAdapter.IngredientViewHolder
import com.example.foodRecipes.databinding.ItemIngredientBinding
import com.example.foodRecipes.domain.model.IngredientModel

class IngredientAdapter(
    private val ingredients: List<IngredientModel>
) : RecyclerView.Adapter<IngredientViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return IngredientViewHolder(ItemIngredientBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        holder.bind(ingredients[position])
    }

    override fun getItemCount() = ingredients.size


    class IngredientViewHolder(
        private val binding: ItemIngredientBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(ingredient: IngredientModel) = binding.apply {
            advancedIngredient.text = ingredient.name
            advancedMeasure.text = ingredient.quantity.trim()
        }
    }
}