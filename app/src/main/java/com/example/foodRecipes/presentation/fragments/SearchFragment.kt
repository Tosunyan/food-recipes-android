package com.example.foodRecipes.presentation.fragments

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.foodRecipes.R
import com.example.foodRecipes.data.remote.ApiResponse
import com.example.foodRecipes.data.remote.data.MealsDto
import com.example.foodRecipes.databinding.FragmentSearchBinding
import com.example.foodRecipes.databinding.ItemMealBinding
import com.example.foodRecipes.domain.mapper.toMealModel
import com.example.foodRecipes.domain.model.MealModel
import com.example.foodRecipes.presentation.adapters.SimpleAdapter
import com.example.foodRecipes.presentation.adapters.holder.MealHolder
import com.example.foodRecipes.presentation.extension.navigate

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: SimpleAdapter<MealModel, MealHolder>

    private var meals: List<MealModel> = ArrayList()

    private val spanCount: Int
        get() =
            if (resources.configuration.orientation == ORIENTATION_LANDSCAPE)
                ORIENTATION_LANDSCAPE
            else ORIENTATION_PORTRAIT

    private val mealClickListener = { _: Int, meal: MealModel ->
        val args = bundleOf(
            "" to meal.name,
            "" to null
        )
        navigate(R.id.fragment_description, args)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun initRecyclerView(mealResponse: ApiResponse.Success<MealsDto>?) {
        adapter = SimpleAdapter(
            items = mealResponse?.data?.meals?.map { it.toMealModel() }?.toMutableList() ?: mutableListOf(),
            itemClickListener = mealClickListener
        ) {
            val itemBinding = ItemMealBinding.inflate(layoutInflater, it, false)
            MealHolder(itemBinding)
        }

//        binding.mealsList.setHasFixedSize(true)
//        binding.mealsList.adapter = adapter
//        binding.mealsList.layoutManager = GridLayoutManager(context, spanCount, VERTICAL, false)
        val oldCount = meals.size
        meals = mealResponse!!.data.meals.map { it.toMealModel() }
        adapter.notifyItemRangeInserted(oldCount, meals.size)
    }
}