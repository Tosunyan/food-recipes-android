package com.example.foodRecipes.fragments

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.foodRecipes.R
import com.example.foodRecipes.adapters.AreaAdapter
import com.example.foodRecipes.adapters.AreaAdapter.AreaItemClickListener
import com.example.foodRecipes.adapters.CategoryAdapter
import com.example.foodRecipes.adapters.CategoryAdapter.CategoryItemClickListener
import com.example.foodRecipes.databinding.FragmentHomeBinding
import com.example.foodRecipes.fragments.HomeFragmentDirections.*
import com.example.foodRecipes.models.Meal
import com.example.foodRecipes.utilities.*
import com.example.foodRecipes.viewmodels.HomeFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class HomeFragment : Fragment(), CategoryItemClickListener, AreaItemClickListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var meal: Meal
    private val viewModel by viewModels<HomeFragmentViewModel>()
    private var categorySpanCount: Int = 0
    private var areaSpanCount: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater)

        initialization()
        onClick()

        return binding.root
    }

    private fun initialization() = binding.apply {
        val title = activity?.findViewById<AppCompatTextView>(R.id.tv_title)!!
        title.visibility = VISIBLE; title.text = APP_NAME
        activity?.findViewById<ConstraintLayout>(R.id.toolbar)?.visibility = VISIBLE
        activity?.findViewById<View>(R.id.spacer)?.visibility = VISIBLE
        activity?.findViewById<ProgressBar>(R.id.progress_bar)?.visibility = VISIBLE
        activity?.findViewById<AppCompatImageView>(R.id.btn_backToHome)?.visibility = GONE
        activity?.findViewById<AppCompatImageView>(R.id.btn_showDescription)?.visibility = GONE
        activity?.findViewById<AppCompatEditText>(R.id.et_search)?.visibility = GONE

        if (resources.configuration.orientation == ORIENTATION_LANDSCAPE) {
            categorySpanCount = 4
            areaSpanCount = 3
        } else {
            categorySpanCount = 3
            areaSpanCount = 4
        }

        categoryList.setHasFixedSize(true)
        areaList.setHasFixedSize(true)

        categoryList.layoutManager = GridLayoutManager(context, categorySpanCount, VERTICAL, false)
        areaList.layoutManager = StaggeredGridLayoutManager(areaSpanCount, HORIZONTAL)


        getRandomMeal()
        getCategories()
        getAreas()

        activity?.findViewById<ProgressBar>(R.id.progress_bar)?.visibility = GONE
    }


    private fun getRandomMeal() = viewModel.getRandomMeal().observe(viewLifecycleOwner) { response ->
        CoroutineScope(Dispatchers.IO).launch {
            meal = response!!.meals[0]
            withContext(Dispatchers.Main) {
                binding.includedMealItem.meal = meal
            }
        }
    }

    private fun getCategories() = viewModel.getCategories().observe(viewLifecycleOwner) { response ->
        CoroutineScope(Dispatchers.IO).launch {
            val categories = response?.categories
            withContext(Dispatchers.Main) {
                binding.categoryList.adapter = CategoryAdapter(categories!!, this@HomeFragment)
            }
        }
    }

    private fun getAreas() = viewModel.getAreas().observe(viewLifecycleOwner) { response ->
        CoroutineScope(Dispatchers.IO).launch {
            val meals = response?.meals
            withContext(Dispatchers.Main) {
                binding.areaList.adapter = AreaAdapter(meals!!, this@HomeFragment)
            }
        }
    }


    override fun onCategoryClick(categoryName: String, description: String) = findNavController()
        .navigate(fromHomeToMeals(CATEGORY, categoryName, description))

    override fun onAreaClick(area: String) = findNavController()
        .navigate(fromHomeToMeals(AREA, area, null))

    private fun onClick() = binding.apply {
        includedMealItem.root.setOnClickListener {
            findNavController().navigate(toDescriptionFragment(null, meal))
        }
    }
}