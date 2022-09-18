package com.example.foodRecipes.presentation.recyclerview.adapter

import android.view.ViewGroup
import com.example.foodRecipes.presentation.recyclerview.holder.SelectableViewHolder

abstract class SelectableAdapter<Item, ViewHolder : SelectableViewHolder<Item>>(
    items: List<Item>,
    itemClickListener: ((position: Int, item: Item) -> Unit)? = null,
    holderFactoryMethod: (parent: ViewGroup) -> ViewHolder,
) : SimpleAdapter<Item, ViewHolder>(items.toMutableList(), itemClickListener, holderFactoryMethod) {

    val selectedItems = mutableListOf<Item>()

    abstract fun setSelectedItem(index: Int)

    abstract fun isItemSelected(index: Int): Boolean

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(items[position], isItemSelected(position))
    }

    fun setSelectedItem(item: Item) {
        setSelectedItem(items.indexOf(item))
    }

    fun resetSelectedItems() {
        selectedItems.clear()
        notifyItemRangeChanged(0, itemCount)
    }

    fun notifyItemsRemoved() {
        selectedItems.forEach { selectedItem ->
            notifyItemRemoved(items.indexOf(selectedItem))
        }
    }

    fun itemForIndex(index: Int) : Item {
        return items[index]
    }
}