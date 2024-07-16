package com.example.foodRecipes.presentation.fragment

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodRecipes.databinding.FragmentMealsBinding
import com.example.foodRecipes.databinding.ItemMealBinding
import com.example.foodRecipes.domain.model.MealModel
import com.example.foodRecipes.presentation.extension.navigate
import com.example.foodRecipes.presentation.extension.navigateUp
import com.example.foodRecipes.presentation.recyclerview.adapter.SimpleAdapter
import com.example.foodRecipes.presentation.recyclerview.holder.MealHolder
import com.example.foodRecipes.presentation.viewmodel.MealsFragmentViewModel
import com.example.foodRecipes.util.collect

class MealsFragment : Fragment() {

    private val viewModel by viewModels<MealsFragmentViewModel>()

    private var binding: FragmentMealsBinding? = null

    private lateinit var adapter: SimpleAdapter<MealModel, MealHolder>

    private val mealClickListener = { _: Int, item: MealModel ->
        val args = bundleOf(MealDetailsFragment.ARG_ID to item.id)
        navigate(MealDetailsFragment::class, args)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let(viewModel::onArgumentsReceive)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMealsBinding.inflate(inflater, container, false)
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

    private fun FragmentMealsBinding.initViews() {
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

    private fun FragmentMealsBinding.initListeners() {
        backButton.setOnClickListener {
            navigateUp()
        }
    }

    private fun initObservers() {
        with(viewModel) {
            title.collect(viewLifecycleOwner) {
                binding?.title?.text = it
            }

            meals.collect(viewLifecycleOwner, adapter::submitList)
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