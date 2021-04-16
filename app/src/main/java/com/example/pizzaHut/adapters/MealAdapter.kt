package com.example.pizzaHut.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pizzaHut.R
import com.example.pizzaHut.adapters.MealAdapter.MealViewHolder
import com.example.pizzaHut.databinding.MealItemBinding
import com.example.pizzaHut.models.Meal

class MealAdapter(private val meals: List<Meal>,
                  private var mealsItemClickListener: MealsItemClickListener)
    : RecyclerView.Adapter<MealViewHolder>() {

    private var inflater: LayoutInflater? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        try {
            mealsItemClickListener = recyclerView as MealsItemClickListener
        } catch (e: ClassCastException) {
            e.printStackTrace()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        if (inflater == null)
            inflater = LayoutInflater.from(parent.context)
        return MealViewHolder(DataBindingUtil.inflate(inflater!!, R.layout.meal_item, parent, false))
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        holder.setMeal(meals[position])
    }

    override fun getItemCount(): Int {
        return meals.size
    }

    inner class MealViewHolder(private val binding: MealItemBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                mealsItemClickListener.onMealClick(meals[bindingAdapterPosition])
            }

            itemView.setOnLongClickListener {
                mealsItemClickListener.onMealLongClick(meals[bindingAdapterPosition].strMeal!!)
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
        fun onMealLongClick(name: String)
    }
}