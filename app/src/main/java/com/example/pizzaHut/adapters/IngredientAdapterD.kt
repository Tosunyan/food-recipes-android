package com.example.pizzaHut.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pizzaHut.R
import com.example.pizzaHut.adapters.IngredientAdapterD.IngredientViewHolderD
import com.example.pizzaHut.databinding.IngredientItemBinding
import com.example.pizzaHut.models.IngredientD

class IngredientAdapterD(private val ingredients: List<IngredientD>) : RecyclerView.Adapter<IngredientViewHolderD>() {

    private var layoutInflater: LayoutInflater? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolderD {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }
        return IngredientViewHolderD(DataBindingUtil.inflate(layoutInflater!!, R.layout.ingredient_item, parent, false))
    }

    override fun onBindViewHolder(holder: IngredientViewHolderD, position: Int) {
        if (ingredients[position].ingredient != "" && ingredients[position].ingredient != null) {
            holder.binding.ingredient = ingredients[position]
            holder.binding.isNull = false
        } else holder.binding.isNull = true
    }

    override fun getItemCount() = ingredients.size

    class IngredientViewHolderD(val binding: IngredientItemBinding) : RecyclerView.ViewHolder(binding.root)
}