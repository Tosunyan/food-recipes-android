package com.example.foodRecipes.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.foodRecipes.R
import com.example.foodRecipes.adapters.MealAdapter.MealViewHolder
import com.example.foodRecipes.databinding.MealItemBinding
import com.example.foodRecipes.models.Meal

class MealAdapter(
    private var listener: MealsItemClickListener
) : ListAdapter<Meal, MealViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder =
        MealViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.meal_item, parent, false))

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) =
        holder.setMeal(getItem(position))


    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Meal>() {
            override fun areItemsTheSame(oldItem: Meal, newItem: Meal) =
                oldItem.idMeal == newItem.idMeal

            override fun areContentsTheSame(oldItem: Meal, newItem: Meal) =
                oldItem == newItem
        }
    }


    inner class MealViewHolder(private val binding: MealItemBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                listener.onMealClick(getItem(adapterPosition))
            }

            itemView.setOnLongClickListener {
                listener.onMealLongClick(getItem(adapterPosition).idMeal)
                return@setOnLongClickListener true
            }
        }

        fun setMeal(meal: Meal) {
            binding.meal = meal
            binding.executePendingBindings()
        }
    }

    interface MealsItemClickListener {
        fun onMealClick(meal: Meal)
        fun onMealLongClick(id: String)
    }
}