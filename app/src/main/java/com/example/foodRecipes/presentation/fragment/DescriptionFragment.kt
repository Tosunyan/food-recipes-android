package com.example.foodRecipes.presentation.fragment

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
import com.example.foodRecipes.datasource.remote.api.ApiResponse
import com.example.foodRecipes.datasource.remote.data.MealDetailsDto
import com.example.foodRecipes.databinding.FragmentDescriptionBinding
import com.example.foodRecipes.databinding.ItemIngredientBinding
import com.example.foodRecipes.domain.mapper.toMealModel
import com.example.foodRecipes.domain.model.MealModel
import com.example.foodRecipes.presentation.recyclerview.holder.IngredientHolder
import com.example.foodRecipes.presentation.recyclerview.adapter.SimpleAdapter
import com.example.foodRecipes.presentation.viewmodel.DescriptionFragmentViewModel

class DescriptionFragment : Fragment() {

    private val viewModel by viewModels<DescriptionFragmentViewModel>()
    private lateinit var binding: FragmentDescriptionBinding

    private lateinit var meal: MealModel

    private val mealObserver = Observer<ApiResponse<MealDetailsDto>> { response ->
        if (response is ApiResponse.Success) {
            meal = response.data.toMealModel()
            init()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDescriptionBinding.inflate(inflater, container, false)

        if (requireArguments()[ARG_MODEL] != null) {
            fromParcelable()
        } else {
            fromApi()
        }

        setupClickListeners()

        return binding.root
    }

    private fun fromApi() {
        val mealId = requireArguments().getString(ARG_ID)!!
        viewModel.getMealDetails(mealId).observe(viewLifecycleOwner, mealObserver)
    }

    private fun fromParcelable() {
        meal = requireArguments().getParcelable(ARG_MODEL)!!
        init()
    }

    private fun init() = binding.apply {
        ivMeal.load(meal.image)
        tvInstruction.text = meal.instructions
        tvMealCategory.text = meal.category
        tvMealCountry.text = meal.region

        ingredientList.adapter = SimpleAdapter(meal.ingredients?.toMutableList() ?: mutableListOf()) {
            val itemBinding = ItemIngredientBinding.inflate(layoutInflater)
            IngredientHolder(itemBinding)
        }
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

    companion object {

        const val ARG_ID = "arg.id"
        const val ARG_MODEL = "arg.model"
    }
}