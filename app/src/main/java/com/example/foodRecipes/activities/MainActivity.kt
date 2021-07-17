package com.example.foodRecipes.activities

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.foodRecipes.R
import com.example.foodRecipes.databinding.ActivityMainBinding
import com.example.foodRecipes.util.NetworkUtil.hasConnection

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val navHostFragment: NavHostFragment
        get() = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment

    private val navController: NavController
        get() = navHostFragment.navController

    private val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
        binding.toolbar.apply {
            when (destination.id) {
                R.id.homeFragment -> {
                    root.visibility = VISIBLE
                    spacer.visibility = VISIBLE
                    btnBackToHome.visibility = GONE
                    btnShowDescription.visibility = GONE
                    etSearch.visibility = GONE
                    spacer.visibility = GONE
                    progressBar.visibility = GONE
                    tvDescription.visibility = GONE

                    tvTitle.visibility = VISIBLE
                    tvTitle.setText(R.string.app_name)

                    binding.bottomNavigation.visibility = VISIBLE
                }

                R.id.searchFragment -> {
                    root.visibility = VISIBLE
                    spacer.visibility = GONE
                    tvTitle.visibility = GONE

                    binding.bottomNavigation.visibility = VISIBLE
                }

                R.id.databaseFragment -> {
                    root.visibility = VISIBLE
                    etSearch.visibility = GONE

                    tvTitle.visibility = VISIBLE
                    tvTitle.setText(R.string.added_to_favorites)

                    binding.bottomNavigation.visibility = VISIBLE
                }

                R.id.mealsFragment -> {
                    root.visibility = VISIBLE
                    spacer.visibility = VISIBLE

                    tvTitle.text = title

                    binding.bottomNavigation.visibility = GONE
                }

                R.id.descriptionFragment -> {
                    root.visibility = GONE

                    binding.bottomNavigation.visibility = GONE
                }
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initNavigation()
    }

    override fun onResume() {
        super.onResume()

        navController.addOnDestinationChangedListener(listener)
    }

    override fun onPause() {
        super.onPause()

        navController.removeOnDestinationChangedListener(listener)
    }


    private fun initNavigation() {
        val graph = navController.navInflater.inflate(R.navigation.nav_graph)

        graph.startDestination = when (hasConnection()) {
            true -> R.id.homeFragment
            false -> R.id.databaseFragment
        }

        navController.graph = graph

        binding.bottomNavigation.setupWithNavController(navController)
    }
}