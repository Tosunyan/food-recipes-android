package com.example.foodRecipes.fragments

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.foodRecipes.adapters.AreaAdapter
import com.example.foodRecipes.adapters.AreaAdapter.AreaItemClickListener
import com.example.foodRecipes.adapters.CategoryAdapter
import com.example.foodRecipes.adapters.CategoryAdapter.CategoryItemClickListener
import com.example.foodRecipes.databinding.FragmentHomeBinding
import com.example.foodRecipes.fragments.Actions.*
import com.example.foodRecipes.fragments.HomeFragmentDirections.*
import com.example.foodRecipes.fragments.MealsFragment.*
import com.example.foodRecipes.models.Meal
import com.example.foodRecipes.util.*
import com.example.foodRecipes.viewmodels.HomeFragmentViewModel

class HomeFragment : Fragment(),
    CategoryItemClickListener,
    AreaItemClickListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var meal: Meal

    private val viewModel by viewModels<HomeFragmentViewModel>()

    private val categorySpanCount: Int
        get() = if (resources.configuration.orientation == ORIENTATION_LANDSCAPE) 4 else 3

    private val areaSpanCount: Int
        get() = if (resources.configuration.orientation == ORIENTATION_LANDSCAPE) 3 else 4


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecyclerViews()
        initClickListeners()

        getRandomMeal()
        getCategories()
        getAreas()

        viewModel.getRandomMeal()
        viewModel.getCategories()
        viewModel.getAreas()
    }


    private fun initRecyclerViews() {
        binding.categoryList.setHasFixedSize(true)
        binding.areaList.setHasFixedSize(true)

        binding.categoryList.layoutManager = GridLayoutManager(context, categorySpanCount, VERTICAL, false)
        binding.areaList.layoutManager = StaggeredGridLayoutManager(areaSpanCount, HORIZONTAL)
    }

    private fun getRandomMeal() {
        viewModel.randomMealData.observe(viewLifecycleOwner) { response ->
            response ?: return@observe

            meal = response.meals[0]
            binding.mealItem.meal = meal
        }
    }

    private fun getCategories() {
        viewModel.categoryData.observe(viewLifecycleOwner) { response ->
            response ?: return@observe

            binding.categoryList.adapter = CategoryAdapter(response.categories, this@HomeFragment)
        }
    }

    private fun getAreas() {
        viewModel.areaData.observe(viewLifecycleOwner) { response ->
            response ?: return@observe

            binding.areaList.adapter = AreaAdapter(response.meals, this@HomeFragment)
        }
    }

    private fun initClickListeners() {
        binding.mealItem.root.setOnClickListener {
            findNavController().navigate(toDescriptionFragment(null, meal))
        }
    }


    override fun onCategoryClick(categoryName: String, description: String) =
        findNavController().navigate(fromHomeToMeals(CATEGORY, categoryName, description))

    override fun onAreaClick(area: String) =
        findNavController().navigate(fromHomeToMeals(AREA, area, null))
}