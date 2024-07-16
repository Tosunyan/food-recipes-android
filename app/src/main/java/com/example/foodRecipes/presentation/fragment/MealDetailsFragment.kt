package com.example.foodRecipes.presentation.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import com.example.foodRecipes.R
import com.example.foodRecipes.databinding.FragmentMealDetailsBinding
import com.example.foodRecipes.databinding.ItemIngredientBinding
import com.example.foodRecipes.domain.model.MealModel
import com.example.foodRecipes.presentation.recyclerview.adapter.SimpleAdapter
import com.example.foodRecipes.presentation.recyclerview.holder.IngredientHolder
import com.example.foodRecipes.presentation.viewmodel.MealDetailsViewModel
import com.example.foodRecipes.util.collect
import kotlinx.coroutines.flow.filterNotNull

class MealDetailsFragment : Fragment() {

    private val viewModel by viewModels<MealDetailsViewModel>()

    private var binding: FragmentMealDetailsBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let(viewModel::onArgumentsReceive)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMealDetailsBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            initClickListeners()

            viewModel.mealDetails
                .filterNotNull()
                .collect(viewLifecycleOwner, ::initViews)
        }
    }

    private fun initViews(mealDetails: MealModel) = binding?.apply {
        ivMeal.load(mealDetails.thumbnail)
        tvInstruction.text = mealDetails.instructions
        tvMealCategory.text = mealDetails.category
        tvMealCountry.text = mealDetails.region

        ingredientList.adapter = SimpleAdapter(mealDetails.ingredients.toMutableList()) {
            val itemBinding = ItemIngredientBinding.inflate(layoutInflater, it, false)
            IngredientHolder(itemBinding)
        }
        tvInstruction.isVisible = true
        ingredientList.isVisible = true
    }

    private fun FragmentMealDetailsBinding.initClickListeners() {
        btnBackToMeals.setOnClickListener {
            requireActivity().onBackPressed()
            btnBackToMeals.performHapticFeedback(1)
        }

        btnSaveMeal.setOnClickListener {
//            CoroutineScope(Dispatchers.IO).launch { viewModel.insertMeal(meal) }
            btnSaveMeal.setImageResource(R.drawable.ic_like_filled)
            Toast.makeText(context, "Added to Database", Toast.LENGTH_SHORT).show()
            btnSaveMeal.performHapticFeedback(1)
        }

        btnSourceLink.setOnClickListener {
            viewModel.mealDetails.value?.sourceUrl?.let {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
                startActivity(intent)
            } ?: run {
                Toast.makeText(it.context, "Link is NULL", Toast.LENGTH_SHORT).show()
            }
        }

        btnYoutubeLink.setOnClickListener {
            viewModel.mealDetails.value?.youtubeUrl?.let {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
                startActivity(intent)
            } ?: run {
                Toast.makeText(it.context, "Link is NULL", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {

        const val ARG_ID = "arg.id"
        const val ARG_MODEL = "arg.model"
    }
}