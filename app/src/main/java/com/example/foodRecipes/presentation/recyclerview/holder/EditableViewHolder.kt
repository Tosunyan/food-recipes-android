package com.example.foodRecipes.presentation.recyclerview.holder

import android.view.View

abstract class EditableViewHolder<Item>(rootView: View) : SelectableViewHolder<Item>(rootView) {

    override fun onBind(item: Item, isSelected: Boolean) = Unit
    abstract fun onBind(item: Item, isSelected: Boolean, isInEditMode: Boolean)
}