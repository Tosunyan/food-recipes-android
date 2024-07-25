package com.example.foodRecipes.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.foodRecipes.domain.model.MealModel
import com.example.foodRecipes.presentation.extension.navigate
import com.example.foodRecipes.presentation.screens.SearchScreen
import com.example.foodRecipes.presentation.theme.ProvideThemedContent
import com.example.foodRecipes.presentation.viewmodel.SearchViewModel

class SearchFragment : Fragment() {

    private val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = ProvideThemedContent {
        viewModel.onSearchInputChange("")

        SearchScreen(
            meals = viewModel.meals.collectAsState().value,
            onSearchInputChange = viewModel::onSearchInputChange,
            onMealItemClick = ::onMealClick
        )
    }

    private fun onMealClick(meal: MealModel) {
        val args = bundleOf(
            MealDetailsFragment.ARG_MODEL to meal,
        )
        navigate(MealDetailsFragment::class, args)
    }
}