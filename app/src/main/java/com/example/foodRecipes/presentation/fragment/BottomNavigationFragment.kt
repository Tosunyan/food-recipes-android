package com.example.foodRecipes.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.foodRecipes.R
import com.example.foodRecipes.databinding.FragmentBottomNavigationBinding

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
        val navHostFragment = childFragmentManager.findFragmentById(R.id.bottom_navigation_fragment_container) as NavHostFragment
        bottomNavigationView.setupWithNavController(navHostFragment.navController)
        bottomNavigationView.setOnItemReselectedListener {
            // Intentionally left blank
        }
    }
}