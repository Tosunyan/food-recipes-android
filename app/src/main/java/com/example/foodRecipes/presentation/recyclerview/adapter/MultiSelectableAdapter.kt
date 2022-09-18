package com.example.foodRecipes.presentation.recyclerview.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import com.example.foodRecipes.presentation.recyclerview.holder.SelectableViewHolder

@SuppressLint("NotifyDataSetChanged")
open class MultiSelectableAdapter<Item, ViewHolder : SelectableViewHolder<Item>>(
    items: List<Item> = emptyList(),
    itemClickListener: ((position: Int, item: Item) -> Unit)? = null,
    holderFactoryMethod: (parent: ViewGroup) -> ViewHolder,
) : SelectableAdapter<Item, ViewHolder>(items.toMutableList(), itemClickListener, holderFactoryMethod) {

    override fun setSelectedItem(index: Int) {
        val item = items[index]
        if (selectedItems.contains(item)) {
            selectedItems.remove(item)
        } else {
            selectedItems.add(item)
        }

        notifyItemChanged(items.indexOf(item))
    }

    override fun isItemSelected(index: Int) =
        selectedItems.contains(items[index])

    fun removeItems(elements: List<Item>) {
        items.removeAll(elements)
        notifyItemsRemoved()
    }
}