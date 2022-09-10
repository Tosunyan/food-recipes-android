package com.example.foodRecipes.presentation.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import coil.load
import com.example.foodRecipes.R
import com.example.foodRecipes.data.remote.ApiResponse
import com.example.foodRecipes.data.remote.data.MealsDto
import com.example.foodRecipes.databinding.FragmentDescriptionBinding
import com.example.foodRecipes.domain.mapper.toMealModel
import com.example.foodRecipes.domain.model.MealModel
import com.example.foodRecipes.presentation.adapters.IngredientAdapter
import com.example.foodRecipes.presentation.fragments.DescriptionFragmentArgs.fromBundle
import com.example.foodRecipes.presentation.viewmodels.DescriptionFragmentViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DescriptionFragment : Fragment() {

    private val viewModel by viewModels<DescriptionFragmentViewModel>()
    private lateinit var binding: FragmentDescriptionBinding
    private lateinit var meal: MealModel

    private val mealObserver = Observer<ApiResponse<MealsDto>> { response ->
        if (response is ApiResponse.Success) {
            meal = response.data.meals[0].toMealModel()
            init()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDescriptionBinding.inflate(inflater, container, false)

        if (fromBundle(requireArguments()).mealModel != null)
            fromParcelable()
        else fromApi()

        setupClickListeners()

        return binding.root
    }

    private fun fromApi() {
        viewModel.getMealDetails(fromBundle(requireArguments()).id!!).observe(viewLifecycleOwner, mealObserver)
    }

    private fun fromParcelable() {
        meal = fromBundle(requireArguments()).mealModel!!
        init()
    }

    private fun init() = binding.apply {
        ivMeal.load(meal.image)
        tvInstruction.text = meal.instructions
        tvMealCategory.text = meal.category
        tvMealCountry.text = meal.region

        ingredientList.adapter = IngredientAdapter(meal.ingredients ?: emptyList())
        tvInstruction.visibility = View.VISIBLE
        ingredientList.visibility = View.VISIBLE
    }

    private fun setupClickListeners() {
        binding.btnBackToMeals.setOnClickListener {
            requireActivity().onBackPressed()
            binding.btnBackToMeals.performHapticFeedback(1)
        }

        binding.btnSaveMeal.setOnClickListener {
//            CoroutineScope(Dispatchers.IO).launch { viewModel.insertMeal(meal) }
            binding.btnSaveMeal.setImageResource(R.drawable.ic_like_filled)
            Toast.makeText(context, "Added to Database", Toast.LENGTH_SHORT).show()
            binding.btnSaveMeal.performHapticFeedback(1)
        }

        binding.btnSourceLink.setOnClickListener {
            if (meal.sourceUrl == null) {
                Toast.makeText(it.context, "Link is NULL", Toast.LENGTH_SHORT).show()
            } else {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(meal.sourceUrl)))
            }
        }

        binding.btnYoutubeLink.setOnClickListener {
            if (meal.youtubeUrl == null) {
                Toast.makeText(it.context, "Link is NULL", Toast.LENGTH_SHORT).show()
            } else {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(meal.youtubeUrl)))
            }
        }
    }
}