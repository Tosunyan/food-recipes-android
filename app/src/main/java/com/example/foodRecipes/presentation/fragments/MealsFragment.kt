package com.example.foodRecipes.presentation.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodRecipes.R
import com.example.foodRecipes.presentation.adapters.MealAdapter
import com.example.foodRecipes.presentation.adapters.MealAdapter.MealsItemClickListener
import com.example.foodRecipes.databinding.FragmentMealsBinding
import com.example.foodRecipes.presentation.fragments.Actions.*
import com.example.foodRecipes.presentation.fragments.MealsFragmentArgs.fromBundle
import com.example.foodRecipes.presentation.fragments.MealsFragmentDirections.toDescriptionFragment
import com.example.foodRecipes.data.models.Meal
import com.example.foodRecipes.data.remote.responses.MealResponse
import com.example.foodRecipes.presentation.viewmodels.MealsFragmentViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MealsFragment : Fragment(), MealsItemClickListener {

    private val viewModel by viewModels<MealsFragmentViewModel>()
    private val adapter = MealAdapter(this@MealsFragment)
    private lateinit var binding: FragmentMealsBinding
    private lateinit var title: String
    private lateinit var description: String

    private lateinit var btnShowDescription: AppCompatImageView
    private lateinit var btnBackToHome: AppCompatImageView
    private lateinit var tvDescription: AppCompatTextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMealsBinding.inflate(inflater)

        init()
        setupClickListeners()

        return binding.root
    }

    private fun init() {
        title = fromBundle(requireArguments()).title

        btnShowDescription = activity?.findViewById(R.id.btn_showDescription)!!
        btnBackToHome = activity?.findViewById(R.id.btn_backToHome)!!
        tvDescription = activity?.findViewById(R.id.tv_description)!!

        btnBackToHome.visibility = VISIBLE

        val spanCount = if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 2 else 1

        when (arguments?.getSerializable("action")) {
            CATEGORY -> {
                description = fromBundle(requireArguments()).description!!
                viewModel.filterMealsByCategory(title).observe(requireActivity(), this::getMeals)
                btnShowDescription.visibility = VISIBLE
                tvDescription.text = description
            }

            AREA -> {
                btnShowDescription.visibility = GONE
                viewModel.filterMealsByArea(title).observe(requireActivity(), this::getMeals)
            }
        }

        binding.mealsList.adapter = adapter
        binding.mealsList.layoutManager = GridLayoutManager(context, spanCount)
    }

    private fun getMeals(mealResponse: MealResponse?) = adapter.submitList(mealResponse?.meals)

    private fun setupClickListeners() {
        btnBackToHome.setOnClickListener {
            findNavController(binding.root).navigateUp()
            btnBackToHome.performHapticFeedback(1)
        }

        btnShowDescription.setOnClickListener {
            tvDescription.visibility = if (tvDescription.visibility == GONE) VISIBLE else GONE
            btnBackToHome.performHapticFeedback(1)
        }
    }


    override fun onMealClick(meal: Meal) =
        findNavController().navigate(toDescriptionFragment(meal.idMeal, null))

    override fun onMealLongClick(id: String) = viewModel.getMealInfo(id).observe(viewLifecycleOwner, { response ->
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.insertMeal(response.meals[0])
        }

        Snackbar
            .make(requireView(), "Added to Database", Snackbar.LENGTH_SHORT)
            .setText(R.string.added_to_favorites)
            .show()
    })
}

enum class Actions {
    CATEGORY,
    AREA
}