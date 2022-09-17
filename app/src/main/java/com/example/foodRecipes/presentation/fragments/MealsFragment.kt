package com.example.foodRecipes.presentation.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodRecipes.R
import com.example.foodRecipes.data.remote.ApiResponse
import com.example.foodRecipes.data.remote.data.MealsDto
import com.example.foodRecipes.databinding.FragmentMealsBinding
import com.example.foodRecipes.databinding.ItemMealBinding
import com.example.foodRecipes.domain.mapper.toMealModel
import com.example.foodRecipes.domain.model.MealModel
import com.example.foodRecipes.presentation.adapters.SimpleAdapter
import com.example.foodRecipes.presentation.adapters.holder.MealHolder
import com.example.foodRecipes.presentation.extension.navigate
import com.example.foodRecipes.presentation.viewmodels.MealsFragmentViewModel

class MealsFragment : Fragment() {

    private val viewModel by viewModels<MealsFragmentViewModel>()

    private var binding: FragmentMealsBinding? = null

    private lateinit var adapter: SimpleAdapter<MealModel, MealHolder>

    private lateinit var title: String
    private lateinit var description: String

    private val mealsObserver = Observer<ApiResponse<MealsDto>> { response ->
        if (response is ApiResponse.Success) {
            adapter.submitList(response.data.meals!!.map { it.toMealModel() })
        }
    }

    private val mealClickListener = { _: Int, item: MealModel ->
        val args = bundleOf(DescriptionFragment.ARG_ID to item.id)
        navigate(R.id.fragment_description, args)
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
        title = requireArguments().getString(ARG_TITLE)!!

        when (requireArguments().getSerializable(ARG_ACTION)) {
            Action.CATEGORY -> {
                description = requireArguments().getString(ARG_DESCRIPTION)!!
                viewModel.filterMealsByCategory(title).observe(viewLifecycleOwner, mealsObserver)
            }
            Action.AREA -> {
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

    companion object {

        const val ARG_TITLE = "arg.title"
        const val ARG_ACTION = "arg.action"
        const val ARG_DESCRIPTION = "arg.description"
    }

    enum class Action {
        CATEGORY,
        AREA
    }
}