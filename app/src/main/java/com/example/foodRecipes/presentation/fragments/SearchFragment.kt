package com.example.foodRecipes.presentation.fragments

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.example.foodRecipes.data.models.Meal
import com.example.foodRecipes.data.remote.responses.MealResponse
import com.example.foodRecipes.databinding.FragmentMealsBinding
import com.example.foodRecipes.presentation.adapters.MealAdapter
import com.example.foodRecipes.presentation.adapters.MealAdapter.MealsItemClickListener
import com.example.foodRecipes.presentation.fragments.SearchFragmentDirections.toDescriptionFragment
import com.example.foodRecipes.presentation.viewmodels.SearchViewModel

class SearchFragment : Fragment(), MealsItemClickListener {

    private val viewModel by viewModels<SearchViewModel>()
    private lateinit var binding: FragmentMealsBinding
    private lateinit var adapter: MealAdapter

    private var meals: List<Meal> = ArrayList()

    private val spanCount: Int
        get() =
            if (resources.configuration.orientation == ORIENTATION_LANDSCAPE)
                ORIENTATION_LANDSCAPE
            else ORIENTATION_PORTRAIT


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMealsBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun initRecyclerView(mealResponse: MealResponse?) {
        adapter = MealAdapter(this@SearchFragment)
        adapter.submitList(mealResponse?.meals)
        binding.mealsList.setHasFixedSize(true)
        binding.mealsList.adapter = adapter
        binding.mealsList.layoutManager = GridLayoutManager(context, spanCount, VERTICAL, false)
        val oldCount = meals.size
        meals = mealResponse!!.meals
        adapter.notifyItemRangeInserted(oldCount, meals.size)
    }

    override fun onMealClick(meal: Meal) = findNavController()
        .navigate(toDescriptionFragment(meal.strMeal, null))

    override fun onMealLongClick(id: String) = Unit
}