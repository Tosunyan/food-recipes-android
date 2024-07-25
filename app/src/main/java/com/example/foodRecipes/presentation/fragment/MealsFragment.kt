package com.example.foodRecipes.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.foodRecipes.domain.model.MealModel
import com.example.foodRecipes.presentation.extension.navigate
import com.example.foodRecipes.presentation.extension.navigateUp
import com.example.foodRecipes.presentation.screens.MealsScreen
import com.example.foodRecipes.presentation.theme.ProvideThemedContent
import com.example.foodRecipes.presentation.viewmodel.MealsFragmentViewModel
import com.example.foodRecipes.util.logException

class MealsFragment : Fragment() {

    private val viewModel by viewModels<MealsFragmentViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            viewModel.onArgumentsReceive(requireArguments())
        } catch (e: Exception) {
            logException(e)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = ProvideThemedContent {
        MealsScreen(
            isLoading = viewModel.isLoading.collectAsState().value,
            title = viewModel.title.collectAsState().value,
            meals = viewModel.meals.collectAsState().value,
            onBackClick = ::navigateUp,
            onMealClick = ::onMealItemClick
        )
    }

    private fun onMealItemClick(item: MealModel) {
        val args = bundleOf(
            MealDetailsFragment.ARG_ID to item.id,
            MealDetailsFragment.ARG_NAME to item.name,
            MealDetailsFragment.ARG_THUMBNAIL to item.thumbnail,
        )
        navigate(MealDetailsFragment::class, args)
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