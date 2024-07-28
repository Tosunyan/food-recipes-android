package com.example.foodRecipes.presentation.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.foodRecipes.presentation.extension.navigateUp
import com.example.foodRecipes.presentation.screens.MealDetailsScreen
import com.example.foodRecipes.presentation.theme.ProvideThemedContent
import com.example.foodRecipes.presentation.viewmodel.MealDetailsViewModel

class MealDetailsFragment : Fragment() {

    private val viewModel by viewModels<MealDetailsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let(viewModel::onArgumentsReceive)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = ProvideThemedContent {
        MealDetailsScreen(
            meal = viewModel.mealDetails.collectAsState().value,
            onBackButtonClick = ::navigateUp,
            onYoutubeClick = ::onLinkClick,
            onSourceClick = ::onLinkClick,
        )
    }

    // TODO Move implementation details to utility file `IntentUtils`
    private fun onLinkClick(uriString: String) {
        val uri = Uri.parse(uriString)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

    companion object {

        const val ARG_MEAL_MODEL = "arg.meal_model"
        const val ARG_MEAL_DETAILS_MODEL = "arg.meal_details_model"
    }
}