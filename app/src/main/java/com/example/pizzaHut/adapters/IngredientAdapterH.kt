package com.example.pizzaHut.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pizzaHut.R
import com.example.pizzaHut.models.IngredientH

class IngredientAdapterH(private val ingredients: List<IngredientH>?,
                         private val ingredientClickListener: IngredientClickListener)
    : RecyclerView.Adapter<IngredientAdapterH.IngredientViewHolderH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolderH {
        return IngredientViewHolderH(LayoutInflater.from(parent.context).inflate(R.layout.area_item, parent, false))
    }

    override fun onBindViewHolder(holder: IngredientViewHolderH, position: Int) {
        holder.ingredientName.text = ingredients?.get(position)?.ingredientName
    }

    override fun getItemCount() = ingredients!!.size

    inner class IngredientViewHolderH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ingredientName: AppCompatTextView = itemView.findViewById(R.id.tv_item)

        init {
            itemView.setOnClickListener {
                ingredientClickListener.onIngredientClick(
                        ingredients?.get(bindingAdapterPosition)?.ingredientName,
                        ingredients?.get(bindingAdapterPosition)?.ingredientDescription
                )
            }
        }
    }

    interface IngredientClickListener {
        fun onIngredientClick(ingredientName: String?, description: String?)
    }
}