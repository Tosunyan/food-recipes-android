package com.example.foodRecipes.presentation.recyclerview.adapter

import android.view.ViewGroup
import com.example.foodRecipes.presentation.recyclerview.holder.SelectableViewHolder

class SingleSelectableAdapter<Item, ViewHolder : SelectableViewHolder<Item>>(
    items: List<Item> = emptyList(),
    itemClickListener: ((position: Int, item: Item) -> Unit)? = null,
    holderFactoryMethod: (parent: ViewGroup) -> ViewHolder
) : SelectableAdapter<Item, ViewHolder>(items.toMutableList(), itemClickListener, holderFactoryMethod) {

    val selectedItemPosition: Int
        get() = if (selectedItems.isEmpty()) 0 else items.indexOf(selectedItems[0])

    override fun setSelectedItem(index: Int) {
        val oldPosition = selectedItemPosition

        selectedItems.apply {
            clear()
            add(items[index])
        }

        notifyItemChanged(oldPosition, Unit)
        notifyItemChanged(index, Unit)
    }

    override fun isItemSelected(index: Int) =
        index == selectedItemPosition
}