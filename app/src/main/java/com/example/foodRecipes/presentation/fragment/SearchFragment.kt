package com.example.foodRecipes.presentation.fragment

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodRecipes.databinding.FragmentSearchBinding
import com.example.foodRecipes.databinding.ItemMealBinding
import com.example.foodRecipes.domain.model.MealModel
import com.example.foodRecipes.presentation.extension.navigate
import com.example.foodRecipes.presentation.recyclerview.adapter.SimpleAdapter
import com.example.foodRecipes.presentation.recyclerview.holder.MealHolder
import com.example.foodRecipes.presentation.viewmodel.SearchViewModel

class SearchFragment : Fragment() {

    private val viewModel: SearchViewModel by viewModels()

    private var binding: FragmentSearchBinding? = null

    private lateinit var adapter: SimpleAdapter<MealModel, MealHolder>

    private val spanCount: Int
        get() = if (resources.configuration.orientation == ORIENTATION_LANDSCAPE) ORIENTATION_LANDSCAPE else ORIENTATION_PORTRAIT

    private val mealsObserver = Observer<List<MealModel>> { meals ->
        adapter.clearList()
        adapter.submitList(meals)
    }

    private val mealClickListener = { _: Int, meal: MealModel ->
        val args = bundleOf(
            "" to meal.name,
            "" to null
        )
        navigate(MealDetailsFragment::class, args)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
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

    private fun FragmentSearchBinding.initViews() {
        adapter = SimpleAdapter(itemClickListener = mealClickListener) {
            val itemBinding = ItemMealBinding.inflate(layoutInflater, it, false)
            MealHolder(itemBinding)
        }

        mealsRecyclerView.adapter = adapter
        mealsRecyclerView.layoutManager = GridLayoutManager(context, spanCount, RecyclerView.VERTICAL, false)
    }

    private fun FragmentSearchBinding.initListeners() {
        searchEditText.doAfterTextChanged { text ->
            viewModel.onSearchInputChange(text?.toString().orEmpty())
        }
    }

    private fun initObservers() {
        viewModel.mealsLiveData.observe(viewLifecycleOwner, mealsObserver)
    }
}