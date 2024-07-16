package com.example.foodRecipes.presentation.fragment

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.LEFT
import androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.foodRecipes.databinding.FragmentMealsBinding
import com.example.foodRecipes.databinding.ItemMealBinding
import com.example.foodRecipes.domain.model.MealModel
import com.example.foodRecipes.presentation.extension.navigate
import com.example.foodRecipes.presentation.recyclerview.adapter.SimpleAdapter
import com.example.foodRecipes.presentation.recyclerview.holder.MealHolder

class DatabaseFragment : Fragment() {

    private lateinit var binding: FragmentMealsBinding
    private lateinit var adapter: SimpleAdapter<MealModel, MealHolder>

    private val spanCount: Int
        get() = if (resources.configuration.orientation == ORIENTATION_LANDSCAPE) 2 else 1

    private val simpleCallback: SimpleCallback = object : SimpleCallback(0, LEFT) {
        override fun onMove(recyclerView: RecyclerView, viewHolder: ViewHolder, target: ViewHolder): Boolean = false

        override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
            // TODO Will be re-implemented later
        }
    }

    private val mealClickListener = { _: Int, meal: MealModel ->
        val args = bundleOf(
            "" to null,
            "" to meal
        )
        navigate(MealDetailsFragment::class, args)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMealsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecyclerView()

        ItemTouchHelper(simpleCallback).attachToRecyclerView(binding.mealsList)
    }

    private fun initRecyclerView() {
        adapter = SimpleAdapter(itemClickListener = mealClickListener) {
            val itemBinding = ItemMealBinding.inflate(layoutInflater, it, false)
            MealHolder(itemBinding)
        }
        binding.mealsList.adapter = adapter
        binding.mealsList.layoutManager = GridLayoutManager(context, spanCount)
    }
}