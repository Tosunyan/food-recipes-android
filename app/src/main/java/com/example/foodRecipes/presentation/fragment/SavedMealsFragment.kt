package com.example.foodRecipes.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.foodRecipes.domain.model.MealDetailsModel
import com.example.foodRecipes.presentation.extension.navigate
import com.example.foodRecipes.presentation.screens.SavedMealsScreen
import com.example.foodRecipes.presentation.theme.ProvideThemedContent
import com.example.foodRecipes.presentation.viewmodel.SavedMealsViewModel

class SavedMealsFragment : Fragment() {

    private val viewModel: SavedMealsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = ProvideThemedContent {
        SavedMealsScreen(
            meals = viewModel.savedMeals.collectAsState().value,
            onMealClick = ::onMealClick,
        )
    }

    private fun onMealClick(item: MealDetailsModel) {
        val args = bundleOf(MealDetailsFragment.ARG_MEAL_DETAILS_MODEL to item)
        navigate(MealDetailsFragment::class, args)
    }
}