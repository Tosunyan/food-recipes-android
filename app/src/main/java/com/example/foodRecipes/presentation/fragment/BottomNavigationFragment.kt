package com.example.foodRecipes.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.foodRecipes.R
import com.example.foodRecipes.databinding.FragmentBottomNavigationBinding
import com.example.foodRecipes.presentation.extension.navigate

class BottomNavigationFragment : Fragment() {

    private var binding: FragmentBottomNavigationBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentBottomNavigationBinding.inflate(inflater)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            initBottomNavigation()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        binding = null
    }

    private fun FragmentBottomNavigationBinding.initBottomNavigation() {
        bottomNavigationView.setOnItemSelectedListener {
            val destinationClass = when (it.itemId) {
                R.id.fragment_home -> HomeFragment::class
                R.id.fragment_search -> SearchFragment::class
                R.id.fragment_saved_meals -> SavedMealsFragment::class
                else -> HomeFragment::class
            }
            navigate(destinationClass, null, R.id.bottom_navigation_fragment_container, childFragmentManager)

            true
        }
        bottomNavigationView.setOnItemReselectedListener {
            // Intentionally left blank
        }
    }
}