package com.example.foodRecipes.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.foodRecipes.databinding.FragmentHomeBinding
import com.example.foodRecipes.databinding.ItemAreaBinding
import com.example.foodRecipes.databinding.ItemCategoryBinding
import com.example.foodRecipes.datasource.remote.data.RegionDto
import com.example.foodRecipes.domain.model.CategoryModel
import com.example.foodRecipes.presentation.extension.isLandscape
import com.example.foodRecipes.presentation.extension.navigate
import com.example.foodRecipes.presentation.extension.showSnackBar
import com.example.foodRecipes.presentation.recyclerview.adapter.SimpleAdapter
import com.example.foodRecipes.presentation.recyclerview.holder.CategoryHolder
import com.example.foodRecipes.presentation.recyclerview.holder.MealHolder
import com.example.foodRecipes.presentation.recyclerview.holder.RegionHolder
import com.example.foodRecipes.presentation.viewmodel.HomeFragmentViewModel
import com.example.foodRecipes.util.collect
import kotlinx.coroutines.flow.filterNotNull

class HomeFragment : Fragment() {

    private val viewModel by viewModels<HomeFragmentViewModel>()

    private var binding: FragmentHomeBinding? = null

    private lateinit var categoryAdapter: SimpleAdapter<CategoryModel, CategoryHolder>
    private lateinit var regionsAdapter: SimpleAdapter<String, RegionHolder>

    private val categoryClickListener = { _: Int, item: CategoryModel ->
        val args = bundleOf(
            MealsFragment.ARG_ACTION to MealsFragment.Action.CATEGORY,
            MealsFragment.ARG_TITLE to item.name,
            MealsFragment.ARG_DESCRIPTION to item.description
        )
        navigate(MealsFragment::class, args)
    }

    private val regionClickListener = { _: Int, region: String ->
        val args = bundleOf(
            MealsFragment.ARG_ACTION to MealsFragment.Action.AREA,
            MealsFragment.ARG_TITLE to region,
        )
        navigate(MealsFragment::class, args)
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
        val categorySpanCount = if (isLandscape) 4 else 3
        val areaSpanCount = if (isLandscape) 3 else 4

        categoryAdapter = SimpleAdapter(itemClickListener = categoryClickListener) { viewGroup ->
            CategoryHolder(ItemCategoryBinding.inflate(layoutInflater, viewGroup, false))
        }
        categoryRecyclerView.adapter = categoryAdapter
        categoryRecyclerView.layoutManager =
            GridLayoutManager(context, categorySpanCount, VERTICAL, false)

        regionsAdapter = SimpleAdapter(itemClickListener = regionClickListener) { viewGroup ->
            RegionHolder(ItemAreaBinding.inflate(layoutInflater, viewGroup, false))
        }
        areaList.adapter = regionsAdapter
        areaList.layoutManager = StaggeredGridLayoutManager(areaSpanCount, HORIZONTAL)
    }

    private fun FragmentHomeBinding.initListeners() {
        mealItem.root.setOnClickListener {
            val mealId = viewModel.randomMeal.value?.id ?: return@setOnClickListener
            val args = bundleOf(DescriptionFragment.ARG_ID to mealId)
            navigate(DescriptionFragment::class, args)
        }
    }

    private fun initObservers() {
        with(viewModel) {
            showErrorMessage.collect(viewLifecycleOwner) {
                showSnackBar(it)
            }

            randomMeal.filterNotNull().collect(viewLifecycleOwner) {
                MealHolder(binding!!.mealItem).onBind(it)
            }

            categories.collect(viewLifecycleOwner) {
                categoryAdapter.clearList()
                categoryAdapter.submitList(it)
            }

            regions.collect(viewLifecycleOwner) {
                regionsAdapter.submitList(it.map(RegionDto::strArea))
            }
        }
    }
}