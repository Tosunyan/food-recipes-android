package com.example.foodRecipes.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.foodRecipes.domain.model.MealDetailsModel
import com.example.foodRecipes.presentation.extension.navigate
import com.example.foodRecipes.presentation.screens.FavoritesScreen
import com.example.foodRecipes.presentation.theme.ProvideThemedContent

class DatabaseFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = ProvideThemedContent {
        FavoritesScreen(
            meals = emptyList(),
            onMealClick = ::onMealClick,
        )
    }

    private fun onMealClick(meal: MealDetailsModel) {
        val args = bundleOf("" to null, "" to meal)
        navigate(MealDetailsFragment::class, args)
    }
}