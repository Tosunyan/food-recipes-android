package com.example.pizzaHut.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pizzaHut.R
import com.example.pizzaHut.adapters.CategoryAdapter.CategoryViewHolder
import com.example.pizzaHut.databinding.CategoryItemBinding
import com.example.pizzaHut.models.Category
import java.lang.Exception

class CategoryAdapter(private val categories: List<Category>,
                      private var categoryItemClickListener: CategoryItemClickListener) :
        RecyclerView.Adapter<CategoryViewHolder>() {

    private var inflater: LayoutInflater? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        try {
            categoryItemClickListener = recyclerView as CategoryItemClickListener
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        if (inflater == null) inflater = LayoutInflater.from(parent.context)
        return CategoryViewHolder(DataBindingUtil.inflate(inflater!!, R.layout.category_item, parent, false))
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bindCategory(categories[position])
    }

    override fun getItemCount() = categories.size

    inner class CategoryViewHolder(private val binding: CategoryItemBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                categoryItemClickListener.onCategoryClick(
                        categories[bindingAdapterPosition].strCategory,
                        categories[bindingAdapterPosition].strCategoryDescription
                )
            }
        }

        fun bindCategory(category: Category) {
            binding.category = category
            binding.executePendingBindings()
        }
    }

    interface CategoryItemClickListener {
        fun onCategoryClick(categoryName: String?, description: String?)
    }
}