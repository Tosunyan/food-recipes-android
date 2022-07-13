package com.example.foodRecipes.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.foodRecipes.presentation.adapters.CategoryAdapter.CategoryViewHolder
import com.example.foodRecipes.databinding.ItemCategoryBinding
import com.example.foodRecipes.data.models.Category

class CategoryAdapter(
    private val categories: List<Category>,
    private var categoryItemClickListener: CategoryItemClickListener
) : RecyclerView.Adapter<CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CategoryViewHolder(ItemCategoryBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categories[position])
    }

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

        fun bind(category: Category) {
            binding.categoryName.text = category.strCategory
            binding.categoryImage.load(category.strCategoryThumb)
        }
    }

    interface CategoryItemClickListener {
        fun onCategoryClick(categoryName: String, description: String)
    }
}