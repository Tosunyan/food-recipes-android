package com.example.foodRecipes.presentation.fragments

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.LEFT
import androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.foodRecipes.data.local.data.MealEntity
import com.example.foodRecipes.data.remote.ApiResponse
import com.example.foodRecipes.domain.model.MealModel
import com.example.foodRecipes.databinding.FragmentMealsBinding
import com.example.foodRecipes.databinding.ItemMealBinding
import com.example.foodRecipes.presentation.adapters.SimpleAdapter
import com.example.foodRecipes.presentation.adapters.holder.MealHolder
import com.example.foodRecipes.presentation.viewmodels.DatabaseViewModel

class DatabaseFragment : Fragment() {

    private lateinit var binding: FragmentMealsBinding
    private lateinit var meals: List<MealModel>
    private lateinit var adapter: SimpleAdapter<MealModel, MealHolder>

    private val viewModel by viewModels<DatabaseViewModel>()

    private val spanCount: Int
        get() = if (resources.configuration.orientation == ORIENTATION_LANDSCAPE) 2 else 1

    private val simpleCallback: SimpleCallback = object : SimpleCallback(0, LEFT) {
        override fun onMove(recyclerView: RecyclerView, viewHolder: ViewHolder, target: ViewHolder): Boolean = false

        override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
            // TODO Will be re-implemented later
        }
    }

    private val mealClickListener = { _: Int, meal: MealModel ->
        findNavController().navigate(DatabaseFragmentDirections.toDescriptionFragment(null, meal))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMealsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecyclerView()

        viewModel.getMealsFromDb().observe(requireActivity(), this::getMeals)

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

    private fun getMeals(meals: List<MealEntity>) {
//        this.meals = meals
//        adapter.submitList(meals)
    }
}