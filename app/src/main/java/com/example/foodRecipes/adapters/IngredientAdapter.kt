package com.example.foodRecipes.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodRecipes.adapters.IngredientAdapter.IngredientViewHolder
import com.example.foodRecipes.databinding.ItemIngredientBinding
import com.example.foodRecipes.models.Ingredient

class IngredientAdapter(
    private val ingredients: List<Ingredient>
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

        fun bind(ingredient: Ingredient) = binding.apply {
            advancedIngredient.text = ingredient.ingredient ?: return@apply
            advancedMeasure.text = ingredient.measure?.trim() ?: return@apply
        }
    }
}