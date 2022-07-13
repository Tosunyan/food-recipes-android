package com.example.foodRecipes.presentation.fragments

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import coil.load
import com.example.foodRecipes.data.models.Category
import com.example.foodRecipes.data.models.Meal
import com.example.foodRecipes.data.remote.responses.CategoryResponse
import com.example.foodRecipes.data.remote.responses.MealResponse
import com.example.foodRecipes.databinding.FragmentHomeBinding
import com.example.foodRecipes.databinding.ItemAreaBinding
import com.example.foodRecipes.databinding.ItemCategoryBinding
import com.example.foodRecipes.presentation.adapters.RegionHolder
import com.example.foodRecipes.presentation.adapters.SimpleAdapter
import com.example.foodRecipes.presentation.adapters.holder.CategoryHolder
import com.example.foodRecipes.presentation.fragments.Actions.AREA
import com.example.foodRecipes.presentation.fragments.HomeFragmentDirections.fromHomeToMeals
import com.example.foodRecipes.presentation.fragments.HomeFragmentDirections.toDescriptionFragment
import com.example.foodRecipes.presentation.viewmodels.HomeFragmentViewModel

class HomeFragment : Fragment() {

    private val viewModel by viewModels<HomeFragmentViewModel>()

    private var binding: FragmentHomeBinding? = null

    private lateinit var categoryAdapter: SimpleAdapter<Category, CategoryHolder>
    private lateinit var regionsAdapter: SimpleAdapter<String, RegionHolder>

    private lateinit var meal: Meal

    private val categoriesObserver = Observer<CategoryResponse> {
        categoryAdapter.clearList()
        categoryAdapter.submitList(it.categories)
    }

    private val regionsObserver = Observer<MealResponse> {
        regionsAdapter.submitList(it.meals.map(Meal::strArea))
    }

    private val mealObserver = Observer<MealResponse> {
        meal = it.meals[0]

        binding?.apply {
            mealItem.mealName.text = meal.strMeal
            mealItem.mealImage.load(meal.strMealThumb)
        }
    }

    private val categoryClickListener = { _: Int, item: Category ->
        findNavController().navigate(fromHomeToMeals(Actions.CATEGORY, item.strCategory, item.strCategoryDescription))
    }

    private val regionClickListener = { _: Int, region: String ->
        findNavController().navigate(fromHomeToMeals(AREA, region, null))
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

        viewModel.apply {
            initObservers()
            getRandomMeal()
            getCategories()
            getAreas()
        }
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
            findNavController().navigate(toDescriptionFragment(null, meal))
        }
    }

    private fun HomeFragmentViewModel.initObservers() {
        categoryData.observe(viewLifecycleOwner, categoriesObserver)
        areaData.observe(viewLifecycleOwner, regionsObserver)
        randomMealData.observe(viewLifecycleOwner, mealObserver)
    }
}