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
import com.example.foodRecipes.presentation.adapters.IngredientAdapter
import com.example.foodRecipes.databinding.FragmentDescriptionBinding
import com.example.foodRecipes.presentation.fragments.DescriptionFragmentArgs.fromBundle
import com.example.foodRecipes.data.models.Ingredient
import com.example.foodRecipes.data.models.Meal
import com.example.foodRecipes.data.remote.ApiResponse
import com.example.foodRecipes.data.remote.responses.MealResponse
import com.example.foodRecipes.presentation.viewmodels.DescriptionFragmentViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class DescriptionFragment : Fragment() {

    private val viewModel by viewModels<DescriptionFragmentViewModel>()
    private lateinit var binding: FragmentDescriptionBinding
    private lateinit var meal: Meal

    private val mealObserver = Observer<ApiResponse<MealResponse>> { response ->
        if (response is ApiResponse.Success) {
            meal = response.data.meals[0]
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
        ivMeal.load(meal.strMealThumb)
        tvInstruction.text = meal.strInstructions
        tvMealCategory.text = meal.strCategory
        tvMealCountry.text = meal.strArea

        ingredientList.adapter = IngredientAdapter(initIngredients(meal))
        tvInstruction.visibility = View.VISIBLE
        ingredientList.visibility = View.VISIBLE
    }

    private fun initIngredients(meal: Meal): List<Ingredient> {
        val ingredients = ArrayList<Ingredient>()

        ingredients.add(Ingredient(meal.strIngredient1, meal.strMeasure1))
        ingredients.add(Ingredient(meal.strIngredient2, meal.strMeasure2))
        ingredients.add(Ingredient(meal.strIngredient3, meal.strMeasure3))
        ingredients.add(Ingredient(meal.strIngredient4, meal.strMeasure4))
        ingredients.add(Ingredient(meal.strIngredient5, meal.strMeasure5))
        ingredients.add(Ingredient(meal.strIngredient6, meal.strMeasure6))
        ingredients.add(Ingredient(meal.strIngredient7, meal.strMeasure7))
        ingredients.add(Ingredient(meal.strIngredient8, meal.strMeasure8))
        ingredients.add(Ingredient(meal.strIngredient9, meal.strMeasure9))
        ingredients.add(Ingredient(meal.strIngredient10, meal.strMeasure10))
        ingredients.add(Ingredient(meal.strIngredient11, meal.strMeasure11))
        ingredients.add(Ingredient(meal.strIngredient12, meal.strMeasure12))
        ingredients.add(Ingredient(meal.strIngredient13, meal.strMeasure13))
        ingredients.add(Ingredient(meal.strIngredient14, meal.strMeasure14))
        ingredients.add(Ingredient(meal.strIngredient15, meal.strMeasure15))
        ingredients.add(Ingredient(meal.strIngredient16, meal.strMeasure16))
        ingredients.add(Ingredient(meal.strIngredient17, meal.strMeasure17))
        ingredients.add(Ingredient(meal.strIngredient18, meal.strMeasure18))
        ingredients.add(Ingredient(meal.strIngredient19, meal.strMeasure19))
        ingredients.add(Ingredient(meal.strIngredient20, meal.strMeasure20))

        return ingredients
    }

    private fun setupClickListeners() {
        binding.btnBackToMeals.setOnClickListener {
            requireActivity().onBackPressed()
            binding.btnBackToMeals.performHapticFeedback(1)
        }

        binding.btnSaveMeal.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch { viewModel.insertMeal(meal) }
            binding.btnSaveMeal.setImageResource(R.drawable.ic_like_filled)
            Toast.makeText(context, "Added to Database", Toast.LENGTH_SHORT).show()
            binding.btnSaveMeal.performHapticFeedback(1)
        }

        binding.btnSourceLink.setOnClickListener {
            if (meal.strSource == null) {
                Toast.makeText(it.context, "Link is NULL", Toast.LENGTH_SHORT).show()
            } else {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(meal.strSource)))
            }
        }

        binding.btnYoutubeLink.setOnClickListener {
            if (meal.strYoutube == null) {
                Toast.makeText(it.context, "Link is NULL", Toast.LENGTH_SHORT).show()
            } else {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(meal.strYoutube)))
            }
        }
    }
}