package com.example.foodRecipes.presentation.adapters.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class SimpleViewHolder<Item>(rootView: View) : RecyclerView.ViewHolder(rootView) {

    open fun onCreate(listener: View.OnClickListener?) {
        itemView.setOnClickListener(listener)
    }

    abstract fun onBind(item: Item): Any
}