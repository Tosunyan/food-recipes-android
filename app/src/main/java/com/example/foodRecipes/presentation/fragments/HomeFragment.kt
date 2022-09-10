package com.example.foodRecipes.presentation.fragments

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import coil.load
import com.example.foodRecipes.R
import com.example.foodRecipes.data.remote.ApiResponse
import com.example.foodRecipes.data.remote.data.CategoriesDto
import com.example.foodRecipes.data.remote.data.MealsDto
import com.example.foodRecipes.data.remote.data.RegionDto
import com.example.foodRecipes.data.remote.data.RegionsDto
import com.example.foodRecipes.databinding.FragmentHomeBinding
import com.example.foodRecipes.databinding.ItemAreaBinding
import com.example.foodRecipes.databinding.ItemCategoryBinding
import com.example.foodRecipes.domain.mapper.toMealModel
import com.example.foodRecipes.domain.model.Category
import com.example.foodRecipes.domain.model.MealModel
import com.example.foodRecipes.presentation.adapters.RegionHolder
import com.example.foodRecipes.presentation.adapters.SimpleAdapter
import com.example.foodRecipes.presentation.adapters.holder.CategoryHolder
import com.example.foodRecipes.presentation.fragments.Actions.AREA
import com.example.foodRecipes.presentation.viewmodels.HomeFragmentViewModel

class HomeFragment : Fragment() {

    private val viewModel by viewModels<HomeFragmentViewModel>()

    private var binding: FragmentHomeBinding? = null

    private lateinit var categoryAdapter: SimpleAdapter<Category, CategoryHolder>
    private lateinit var regionsAdapter: SimpleAdapter<String, RegionHolder>

    private lateinit var meal: MealModel

    private val categoriesObserver = Observer<ApiResponse<CategoriesDto>> { response ->
        if (response is ApiResponse.Success) {
            categoryAdapter.clearList()
            categoryAdapter.submitList(response.data.categories)
        }
    }

    private val regionsObserver = Observer<ApiResponse<RegionsDto>> { response ->
        if (response is ApiResponse.Success) {
            regionsAdapter.submitList(response.data.meals.map(RegionDto::strArea))
        }
    }

    private val mealObserver = Observer<ApiResponse<MealsDto>> { response ->
        if (response is ApiResponse.Success) {
            meal = response.data.meals[0].toMealModel()

            binding?.apply {
                mealItem.mealName.text = meal.name
                mealItem.mealImage.load(meal.image)
            }
        }
    }

    private val categoryClickListener = { _: Int, item: Category ->
        val args = bundleOf(
            "" to Actions.CATEGORY,
            "" to item.strCategory,
            "" to item.strCategoryDescription
        )
        findNavController().navigate(R.id.fragment_meals, args)
    }

    private val regionClickListener = { _: Int, region: String ->
        val args = bundleOf(
            "" to AREA,
            "" to region,
            "" to null
        )
        findNavController().navigate(R.id.fragment_meals, args)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            initViews()
            initListeners()
        }

        initObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        binding = null
    }

    private fun FragmentHomeBinding.initViews() {
        val categorySpanCount = if (resources.configuration.orientation == ORIENTATION_LANDSCAPE) 4 else 3
        val areaSpanCount = if (resources.configuration.orientation == ORIENTATION_LANDSCAPE) 3 else 4

        categoryAdapter = SimpleAdapter(itemClickListener = categoryClickListener) { viewGroup ->
            CategoryHolder(ItemCategoryBinding.inflate(layoutInflater, viewGroup, false))
        }
        categoryRecyclerView.adapter = categoryAdapter
        categoryRecyclerView.layoutManager = GridLayoutManager(context, categorySpanCount, VERTICAL, false)

        regionsAdapter = SimpleAdapter(itemClickListener = regionClickListener) { viewGroup ->
            RegionHolder(ItemAreaBinding.inflate(layoutInflater, viewGroup, false))
        }
        areaList.adapter = regionsAdapter
        areaList.layoutManager = StaggeredGridLayoutManager(areaSpanCount, HORIZONTAL)
    }

    private fun FragmentHomeBinding.initListeners() {
        mealItem.root.setOnClickListener {
            val args = bundleOf(
                "" to meal.name,
                "" to null
            )
            findNavController().navigate(R.id.fragment_description, args)
        }
    }

    private fun initObservers() {
        with(viewModel) {
            getRandomMeal().observe(viewLifecycleOwner, mealObserver)
            getCategories().observe(viewLifecycleOwner, categoriesObserver)
            getAreas().observe(viewLifecycleOwner, regionsObserver)
        }
    }
}