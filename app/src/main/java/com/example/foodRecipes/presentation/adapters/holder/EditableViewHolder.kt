package com.example.foodRecipes.presentation.adapters.holder

import android.view.View

abstract class EditableViewHolder<Item>(rootView: View) : SelectableViewHolder<Item>(rootView) {

    override fun onBind(item: Item, isSelected: Boolean) = Unit
    abstract fun onBind(item: Item, isSelected: Boolean, isInEditMode: Boolean)
}