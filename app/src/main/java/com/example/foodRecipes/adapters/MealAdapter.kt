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

class MealAdapter(private var mealsItemClickListener: MealsItemClickListener)
    : ListAdapter<Meal, MealViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Meal>() {
            override fun areItemsTheSame(oldItem: Meal, newItem: Meal) =
                    oldItem.idMeal == newItem.idMeal

            override fun areContentsTheSame(oldItem: Meal, newItem: Meal) =
                    oldItem == newItem
        }
    }

    private var inflater: LayoutInflater? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        if (inflater == null)
            inflater = LayoutInflater.from(parent.context)
        return MealViewHolder(DataBindingUtil.inflate(inflater!!, R.layout.meal_item, parent, false))
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) =
            holder.setMeal(getItem(position))

    inner class MealViewHolder(private val binding: MealItemBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                mealsItemClickListener.onMealClick(getItem(adapterPosition))
            }

            itemView.setOnLongClickListener {
                mealsItemClickListener.onMealLongClick(getItem(adapterPosition).idMeal)
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