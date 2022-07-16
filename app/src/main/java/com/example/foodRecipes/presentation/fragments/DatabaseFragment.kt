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
import com.example.foodRecipes.data.models.Meal
import com.example.foodRecipes.databinding.FragmentMealsBinding
import com.example.foodRecipes.databinding.ItemMealBinding
import com.example.foodRecipes.presentation.adapters.SimpleAdapter
import com.example.foodRecipes.presentation.adapters.holder.MealHolder
import com.example.foodRecipes.presentation.fragments.DatabaseFragmentDirections.toDescriptionFragment
import com.example.foodRecipes.presentation.viewmodels.DatabaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DatabaseFragment : Fragment() {

    private lateinit var binding: FragmentMealsBinding
    private lateinit var meals: List<Meal>
    private lateinit var adapter: SimpleAdapter<Meal, MealHolder>

    private val viewModel by viewModels<DatabaseViewModel>()

    private val spanCount: Int
        get() = if (resources.configuration.orientation == ORIENTATION_LANDSCAPE) 2 else 1

    private val simpleCallback: SimpleCallback = object : SimpleCallback(0, LEFT) {
        override fun onMove(recyclerView: RecyclerView, viewHolder: ViewHolder, target: ViewHolder): Boolean = false

        override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
            CoroutineScope(Dispatchers.IO).launch { viewModel.deleteMeal(meals[viewHolder.adapterPosition]) }

//            SnackBar
//                .make(binding.root, meals[viewHolder.adapterPosition].strMeal + " deleted", LENGTH_SHORT)
//                .setTextColor(resources.getColor(R.color.colorBackground, activity?.theme))
//                .setBackgroundTint(resources.getColor(R.color.colorText, activity?.theme))
//                .setActionTextColor(resources.getColor(R.color.colorAccent, activity?.theme))
//                .setAction("Undo") {
//                    CoroutineScope(Dispatchers.IO).launch {
//                        viewModel.insertMeal(meals[viewHolder.adapterPosition])
//                    }
//                }
//                .show();
        }
    }

    private val mealClickListener = { _: Int, meal: Meal ->
        findNavController().navigate(toDescriptionFragment(null, meal))
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

    private fun getMeals(meals: List<Meal>) {
        this.meals = meals
        adapter.submitList(meals)
    }
}