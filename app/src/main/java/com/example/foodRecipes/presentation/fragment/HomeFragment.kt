package com.example.foodRecipes.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.foodRecipes.domain.model.CategoryModel
import com.example.foodRecipes.domain.model.MealModel
import com.example.foodRecipes.domain.model.RegionModel
import com.example.foodRecipes.presentation.extension.navigate
import com.example.foodRecipes.presentation.extension.showSnackBar
import com.example.foodRecipes.presentation.screens.HomeScreen
import com.example.foodRecipes.presentation.theme.ProvideThemedContent
import com.example.foodRecipes.presentation.viewmodel.HomeFragmentViewModel
import com.example.foodRecipes.util.collect

class HomeFragment : Fragment() {

    private val viewModel by viewModels<HomeFragmentViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = ProvideThemedContent {
        viewModel.showErrorMessage.collect(viewLifecycleOwner, ::showSnackBar)

        HomeScreen(
            dailySpecial = viewModel.randomMeal.collectAsState().value,
            categories = viewModel.categories.collectAsState().value,
            regions = viewModel.regions.collectAsState().value,
            onDailySpecialClick = ::onDailySpecialClick,
            onCategoryItemClick = ::onCategoryItemClick,
            onRegionItemClick = ::onRegionItemClick
        )
    }

    private fun onDailySpecialClick(model: MealModel) {
        val args = bundleOf(MealDetailsFragment.ARG_MODEL to model)
        navigate(MealDetailsFragment::class, args)
    }

    private fun onCategoryItemClick(item: CategoryModel) {
        val args = bundleOf(
            MealsFragment.ARG_ACTION to MealsFragment.Action.CATEGORY,
            MealsFragment.ARG_TITLE to item.name,
            MealsFragment.ARG_DESCRIPTION to item.description
        )
        navigate(MealsFragment::class, args)
    }

    private fun onRegionItemClick(region: RegionModel) {
        val args = bundleOf(
            MealsFragment.ARG_ACTION to MealsFragment.Action.AREA,
            MealsFragment.ARG_TITLE to region.name,
        )
        navigate(MealsFragment::class, args)
    }
}