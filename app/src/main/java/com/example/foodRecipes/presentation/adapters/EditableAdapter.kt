package com.example.foodRecipes.presentation.adapters

import android.annotation.SuppressLint
import android.view.ViewGroup
import com.example.foodRecipes.presentation.adapters.holder.EditableViewHolder

@SuppressLint("NotifyDataSetChanged")
class EditableAdapter<Item, ViewHolder : EditableViewHolder<Item>>(
    items: List<Item> = emptyList(),
    itemClickListener: ((position: Int, item: Item) -> Unit)? = null,
    holderFactoryMethod: (parent: ViewGroup) -> ViewHolder,
) : MultiSelectableAdapter<Item, ViewHolder>(items.toMutableList(), itemClickListener, holderFactoryMethod) {

    var isInEditMode: Boolean = false
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(items[position], isItemSelected(position), isInEditMode)
    }
}