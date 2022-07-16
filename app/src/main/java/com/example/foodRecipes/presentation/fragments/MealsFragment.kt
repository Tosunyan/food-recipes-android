package com.example.foodRecipes.presentation.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodRecipes.data.models.Meal
import com.example.foodRecipes.data.remote.ApiResponse
import com.example.foodRecipes.data.remote.responses.MealResponse
import com.example.foodRecipes.databinding.FragmentMealsBinding
import com.example.foodRecipes.databinding.ItemMealBinding
import com.example.foodRecipes.presentation.adapters.SimpleAdapter
import com.example.foodRecipes.presentation.adapters.holder.MealHolder
import com.example.foodRecipes.presentation.fragments.Actions.AREA
import com.example.foodRecipes.presentation.fragments.Actions.CATEGORY
import com.example.foodRecipes.presentation.fragments.MealsFragmentArgs.fromBundle
import com.example.foodRecipes.presentation.fragments.MealsFragmentDirections.toDescriptionFragment
import com.example.foodRecipes.presentation.viewmodels.MealsFragmentViewModel

class MealsFragment : Fragment() {

    private val viewModel by viewModels<MealsFragmentViewModel>()

    private var binding: FragmentMealsBinding? = null

    private lateinit var adapter: SimpleAdapter<Meal, MealHolder>

    private lateinit var title: String
    private lateinit var description: String

    private val mealsObserver = Observer<ApiResponse<MealResponse>> { response ->
        if (response is ApiResponse.Success) {
            adapter.submitList(response.data.meals)
        }
    }

    private val mealClickListener = { _: Int, item: Meal ->
        findNavController().navigate(toDescriptionFragment(item.idMeal, null))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMealsBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            initViews()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        binding = null
    }

    private fun FragmentMealsBinding.initViews() {
        title = fromBundle(requireArguments()).title

        when (arguments?.getSerializable("action")) {
            CATEGORY -> {
                description = fromBundle(requireArguments()).description!!
                viewModel.filterMealsByCategory(title).observe(viewLifecycleOwner, mealsObserver)
            }
            AREA -> {
                viewModel.filterMealsByArea(title).observe(viewLifecycleOwner, mealsObserver)
            }
        }

        adapter = SimpleAdapter(itemClickListener = mealClickListener) {
            val itemBinding = ItemMealBinding.inflate(layoutInflater, it, false)
            MealHolder(itemBinding)
        }

        mealsList.apply {
            val spanCount = if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 2 else 1
            layoutManager = GridLayoutManager(context, spanCount)
            adapter = this@MealsFragment.adapter
        }
    }
}

enum class Actions {
    CATEGORY,
    AREA
}