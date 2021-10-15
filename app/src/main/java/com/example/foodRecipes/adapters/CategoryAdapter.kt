package com.example.foodRecipes.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.foodRecipes.R
import com.example.foodRecipes.adapters.CategoryAdapter.CategoryViewHolder
import com.example.foodRecipes.databinding.ItemCategoryBinding
import com.example.foodRecipes.models.Category

class CategoryAdapter(
    private val categories: List<Category>,
    private var categoryItemClickListener: CategoryItemClickListener
) : RecyclerView.Adapter<CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder =
        CategoryViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_category,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) =
        holder.bindCategory(categories[position])

    override fun getItemCount() = categories.size


    inner class CategoryViewHolder(private val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                categoryItemClickListener.onCategoryClick(
                    categories[adapterPosition].strCategory,
                    categories[adapterPosition].strCategoryDescription
                )
            }
        }

        fun bindCategory(category: Category) {
            binding.category = category
            binding.executePendingBindings()
        }
    }

    interface CategoryItemClickListener {
        fun onCategoryClick(categoryName: String, description: String)
    }
}