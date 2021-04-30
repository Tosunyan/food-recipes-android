package com.example.foodRecipes.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodRecipes.R
import com.example.foodRecipes.models.Meal

class AreaAdapter(private val areas: List<Meal>,
        private val onClickListener: AreaItemClickListener)
    : RecyclerView.Adapter<AreaAdapter.AreaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AreaViewHolder =
            AreaViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.area_item, parent, false))

    override fun onBindViewHolder(holder: AreaViewHolder, position: Int) {
        holder.area.text = areas[position].strArea
    }

    override fun getItemCount() = areas.size

    inner class AreaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val area: AppCompatTextView = itemView.findViewById(R.id.tv_item)

        init {
            itemView.setOnClickListener { onClickListener.onAreaClick(areas[adapterPosition].strArea) }
        }
    }

    interface AreaItemClickListener {
        fun onAreaClick(area: String)
    }
}