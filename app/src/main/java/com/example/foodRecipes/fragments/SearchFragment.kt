package com.example.foodRecipes.fragments

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.example.foodRecipes.R
import com.example.foodRecipes.adapters.MealAdapter
import com.example.foodRecipes.adapters.MealAdapter.MealsItemClickListener
import com.example.foodRecipes.databinding.FragmentMealsBinding
import com.example.foodRecipes.fragments.SearchFragmentDirections.toDescriptionFragment
import com.example.foodRecipes.models.Meal
import com.example.foodRecipes.responses.MealResponse
import com.example.foodRecipes.utilities.SimpleTextWatcher
import com.example.foodRecipes.viewmodels.SearchViewModel
import java.util.*
import kotlin.collections.ArrayList

class SearchFragment : Fragment(), MealsItemClickListener {

    private val viewModel by viewModels<SearchViewModel>()
    private lateinit var binding: FragmentMealsBinding
    private lateinit var adapter: MealAdapter
    private lateinit var etSearch: AppCompatEditText
    private var meals: List<Meal> = ArrayList()

    private var timer: Timer? = null
    private var spanCount: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMealsBinding.inflate(inflater, container, false)
        spanCount =
            if (resources.configuration.orientation == ORIENTATION_LANDSCAPE) ORIENTATION_LANDSCAPE else ORIENTATION_PORTRAIT

        getMeals()

        return binding.root
    }

    private fun initRecyclerView(mealResponse: MealResponse?) {
        adapter = MealAdapter(this@SearchFragment)
        adapter.submitList(mealResponse?.meals)
        binding.mealsList.setHasFixedSize(true)
        binding.mealsList.adapter = adapter
        binding.mealsList.layoutManager = GridLayoutManager(context, spanCount, VERTICAL, false)
        val oldCount = meals.size
        meals = mealResponse!!.meals
        adapter.notifyItemRangeInserted(oldCount, meals.size)
    }

    private fun getMeals() {
        activity?.findViewById<AppCompatTextView>(R.id.tv_title)?.visibility = GONE
        activity?.findViewById<ConstraintLayout>(R.id.toolbar)?.visibility = VISIBLE
        activity?.findViewById<View>(R.id.spacer)?.visibility = VISIBLE
        etSearch = activity?.findViewById(R.id.et_search)!!
        etSearch.visibility = VISIBLE

        etSearch.addTextChangedListener(object : SimpleTextWatcher() {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (timer != null) timer!!.cancel()
            }

            override fun afterTextChanged(s: Editable?) {
                if (s.toString().trim().isNotEmpty()) {
                    timer = Timer()
                    timer!!.schedule(object : TimerTask() {
                        override fun run() {
                            Handler(Looper.getMainLooper()).post {
                                if (s?.length == 1) {
                                    viewModel.getMealsByFirstLetter(s[0]).observe(viewLifecycleOwner, { mealResponse ->
                                        initRecyclerView(mealResponse)
                                    })
                                } else {
                                    viewModel.getMealsByName(s.toString())
                                        .observe(viewLifecycleOwner, { mealResponse ->
                                            initRecyclerView(mealResponse)
                                        })
                                }
                            }
                        }
                    }, 800)
                }
            }
        })
    }

    override fun onMealClick(meal: Meal) = findNavController()
        .navigate(toDescriptionFragment(meal.strMeal, null))

    override fun onMealLongClick(id: String) = Unit
}