package com.example.foodRecipes.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.foodRecipes.R
import com.example.foodRecipes.adapters.IngredientAdapter.IngredientViewHolder
import com.example.foodRecipes.databinding.IngredientItemBinding
import com.example.foodRecipes.models.Ingredient

class IngredientAdapter(
    private val ingredients: List<Ingredient>
) : RecyclerView.Adapter<IngredientViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder =
        IngredientViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.ingredient_item,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        if (ingredients[position].ingredient != "" && ingredients[position].ingredient != null) {
            holder.bindIngredient(ingredients[position])
            holder.binding.isNull = false
        } else holder.binding.isNull = true
    }

    override fun getItemCount() = ingredients.size


    class IngredientViewHolder(val binding: IngredientItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindIngredient(ingredient: Ingredient) {
            binding.ingredient = ingredient
            binding.executePendingBindings()
        }
    }
}