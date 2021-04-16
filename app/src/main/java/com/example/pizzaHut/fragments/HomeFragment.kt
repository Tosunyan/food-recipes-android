package com.example.pizzaHut.fragments

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.net.Uri.parse
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.pizzaHut.adapters.AreaAdapter
import com.example.pizzaHut.adapters.AreaAdapter.AreaItemClickListener
import com.example.pizzaHut.adapters.CategoryAdapter
import com.example.pizzaHut.adapters.CategoryAdapter.CategoryItemClickListener
import com.example.pizzaHut.adapters.IngredientAdapterH
import com.example.pizzaHut.adapters.IngredientAdapterH.IngredientClickListener
import com.example.pizzaHut.databinding.FragmentHomeBinding
import com.example.pizzaHut.fragments.HomeFragmentDirections.*
import com.example.pizzaHut.models.Meal
import com.example.pizzaHut.responses.CategoryResponse
import com.example.pizzaHut.responses.IngredientResponse
import com.example.pizzaHut.responses.MealResponse
import com.example.pizzaHut.viewmodels.HomeFragmentViewModel

class HomeFragment : Fragment(), CategoryItemClickListener,
        AreaItemClickListener, IngredientClickListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeFragmentViewModel
    private lateinit var meal: Meal
    private var categorySpanCount = 0
    private var areaSpanCount = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater)
        viewModel = ViewModelProvider(requireActivity()).get(HomeFragmentViewModel::class.java)

        if (resources.configuration.orientation == ORIENTATION_LANDSCAPE) {
            categorySpanCount = 4
            areaSpanCount = 3
        } else {
            categorySpanCount = 2
            areaSpanCount = 4
        }

        getRandomMeal()
        getCategories()
        getAreas()
        getIngredients()

        onClick()
        return binding.root
    }

    private fun getRandomMeal() {
        viewModel.randomMeal.observe(viewLifecycleOwner, { mealResponse: MealResponse ->
            if (mealResponse.meals != null) {
                meal = mealResponse.meals[0]
                binding.includeMealItem.meal = meal
            }
        })
    }

    private fun getCategories() {
        viewModel.categories.observe(viewLifecycleOwner, { categoryResponse: CategoryResponse ->
            if (categoryResponse.categories != null) {
                binding.categoryList.adapter = CategoryAdapter(categoryResponse.categories, this@HomeFragment)
                binding.categoryList.layoutManager = GridLayoutManager(activity, categorySpanCount)
                binding.categoryList.setHasFixedSize(true)
            }
        })
    }

    private fun getAreas() {
        viewModel.areas.observe(viewLifecycleOwner, { mealResponse: MealResponse ->
            if (mealResponse.meals != null) {
                binding.areaList.adapter = AreaAdapter(mealResponse.meals, this@HomeFragment)
                binding.areaList.layoutManager = StaggeredGridLayoutManager(areaSpanCount, HORIZONTAL)
                binding.areaList.setHasFixedSize(true)
            }
        })
    }

    private fun getIngredients() {
        viewModel.ingredients.observe(viewLifecycleOwner, { response: IngredientResponse ->
            if (response.ingredients != null) {
                binding.ingredientList.adapter = IngredientAdapterH(response.ingredients, this@HomeFragment)
                binding.ingredientList.layoutManager = StaggeredGridLayoutManager(7, HORIZONTAL)
                binding.ingredientList.setHasFixedSize(true)
            }
        })
    }


    override fun onCategoryClick(categoryName: String?, description: String?) {
        findNavController(binding.root).navigate(fromHomeToMeals("Category", categoryName!!, description!!))
    }

    override fun onAreaClick(area: String?) {
        findNavController(binding.root).navigate(fromHomeToMeals("Area", area!!, null))
    }

    override fun onIngredientClick(ingredientName: String?, description: String?) {
        findNavController(binding.root).navigate(fromHomeToMeals("Ingredient", ingredientName!!, description))
    }

    private fun onClick() {
        binding.btnDatabase.setOnClickListener { findNavController(binding.root).navigate(fromHomeToMeals("Database", "Favorite Meals", null)) }
        binding.btnSearch.setOnClickListener { findNavController(binding.root).navigate(fromHomeToSearch()) }
        binding.includeMealItem.root.setOnClickListener { findNavController(binding.root).navigate(fromHomeToDescription(null, meal)) }
        binding.tvInstagramLink.setOnClickListener { startActivity(Intent(ACTION_VIEW, parse("https://www.instagram.com/_tosunyan__/"))) }
    }
}