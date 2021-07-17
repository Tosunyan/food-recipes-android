package com.example.foodRecipes.fragments

import android.app.Activity
import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.LEFT
import androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.foodRecipes.R
import com.example.foodRecipes.adapters.MealAdapter
import com.example.foodRecipes.adapters.MealAdapter.MealsItemClickListener
import com.example.foodRecipes.databinding.FragmentMealsBinding
import com.example.foodRecipes.fragments.DatabaseFragmentDirections.toDescriptionFragment
import com.example.foodRecipes.models.Meal
import com.example.foodRecipes.viewmodels.DatabaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DatabaseFragment : Fragment(),
    MealsItemClickListener {

    private lateinit var binding: FragmentMealsBinding
    private lateinit var meals: List<Meal>
    private lateinit var adapter: MealAdapter
    private lateinit var btnShowDescription: AppCompatImageView

    private val viewModel by viewModels<DatabaseViewModel>()

    private val spanCount: Int
        get() = if (resources.configuration.orientation == ORIENTATION_LANDSCAPE) 2 else 1

    private val simpleCallback: SimpleCallback = object : SimpleCallback(0, LEFT) {
        override fun onMove(recyclerView: RecyclerView, viewHolder: ViewHolder, target: ViewHolder): Boolean = false

        override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
            CoroutineScope(Dispatchers.IO).launch { viewModel.deleteMeal(meals[viewHolder.adapterPosition]) }

//            SnackBar
//                .make(binding.root, meals[viewHolder.adapterPosition].strMeal + " deleted", LENGTH_SHORT)
//                .setTextColor(resources.getColor(R.color.colorBackground, activity?.theme))
//                .setBackgroundTint(resources.getColor(R.color.colorText, activity?.theme))
//                .setActionTextColor(resources.getColor(R.color.colorAccent, activity?.theme))
//                .setAction("Undo") {
//                    CoroutineScope(Dispatchers.IO).launch {
//                        viewModel.insertMeal(meals[viewHolder.adapterPosition])
//                    }
//                }
//                .show();
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMealsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        activity?.init()
        initRecyclerView()

        viewModel.getMealsFromDb().observe(requireActivity(), this::getMeals)

        ItemTouchHelper(simpleCallback).attachToRecyclerView(binding.mealsList)
    }


    private fun Activity.init() {
        btnShowDescription = findViewById(R.id.btn_showDescription)
        btnShowDescription.visibility = View.GONE

        //window.statusBarColor = Color.parseColor("#00000000")
    }

    private fun initRecyclerView() {
        adapter = MealAdapter(this@DatabaseFragment)
        binding.mealsList.adapter = adapter
        binding.mealsList.layoutManager = GridLayoutManager(context, spanCount)
    }

    private fun getMeals(meals: List<Meal>) {
        this.meals = meals
        adapter.submitList(meals)
    }


    override fun onMealClick(meal: Meal) =
        findNavController().navigate(toDescriptionFragment(null, meal))

    override fun onMealLongClick(id: String) = Unit
}