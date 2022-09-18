package com.example.foodRecipes.presentation.recyclerview.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodRecipes.presentation.recyclerview.holder.SimpleViewHolder

open class SimpleAdapter<Item, ViewHolder : SimpleViewHolder<Item>>(
    val items: MutableList<Item> = mutableListOf(),
    private val itemClickListener: ((position: Int, item: Item) -> Unit)? = null,
    private val holderFactoryMethod: (parent: ViewGroup) -> ViewHolder,
) : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewHolder = holderFactoryMethod.invoke(parent)

        if (itemClickListener != null) {
            viewHolder.onCreate {
                val position = viewHolder.adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    itemClickListener.invoke(position, items[position])
                }
            }
        } else {
            viewHolder.onCreate(null)
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(items[position])
    }

    override fun getItemCount() = items.size

    val isEmpty: Boolean
        get() = itemCount == 0

    fun submitList(elements: List<Item>) {
        items.addAll(elements)
        notifyItemRangeInserted(itemCount, elements.size)
    }

    fun clearList() {
        notifyItemRangeRemoved(0, itemCount)
        items.clear()
    }

    fun getItemAt(position: Int) = items[position]

    fun removeItem(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }
}