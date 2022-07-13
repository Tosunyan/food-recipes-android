package com.example.foodRecipes.presentation.adapters.holder

import android.view.View

abstract class SelectableViewHolder<Item>(rootView: View) : SimpleViewHolder<Item>(rootView) {
    override fun onBind(item: Item) = Unit
    abstract fun onBind(item: Item, isSelected: Boolean): Any
}