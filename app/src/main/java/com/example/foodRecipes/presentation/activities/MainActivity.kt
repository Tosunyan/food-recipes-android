package com.example.foodRecipes.presentation.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.foodRecipes.R
import com.example.foodRecipes.databinding.ActivityMainBinding
import com.example.foodRecipes.domain.util.NetworkUtil.hasConnection

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

    private val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
        binding?.toolbar?.apply {
            when (destination.id) {
                R.id.homeFragment -> {
                    root.isVisible = true
                    spacer.isVisible = true
                    btnBackToHome.isVisible = false
                    btnShowDescription.isVisible = false
                    etSearch.isVisible = false
                    spacer.isVisible = false
                    progressBar.isVisible = false
                    tvDescription.isVisible = false

                    tvTitle.isVisible = true
                    tvTitle.setText(R.string.app_name)

                    binding!!.bottomNavigation.isVisible = true
                }

                R.id.searchFragment -> {
                    root.isVisible = true
                    spacer.isVisible = false
                    tvTitle.isVisible = false

                    binding!!.bottomNavigation.isVisible = true
                }

                R.id.databaseFragment -> {
                    root.isVisible = true
                    etSearch.isVisible = false

                    tvTitle.isVisible = true
                    tvTitle.setText(R.string.added_to_favorites)

                    binding!!.bottomNavigation.isVisible = true
                }

                R.id.mealsFragment -> {
                    root.isVisible = true
                    spacer.isVisible = true

                    tvTitle.text = title

                    binding!!.bottomNavigation.isVisible = false
                }

                R.id.descriptionFragment -> {
                    root.isVisible = false

                    binding!!.bottomNavigation.isVisible = false
                }
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)


        navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHostFragment.navController

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

    override fun onDestroy() {
        super.onDestroy()

        binding = null
    }


    private fun initNavigation() {
        val graph = navController.navInflater.inflate(R.navigation.nav_graph)

        graph.startDestination = if (hasConnection()) {
            R.id.homeFragment
        } else {
            R.id.databaseFragment
        }

        navController.graph = graph

        binding!!.bottomNavigation.setupWithNavController(navController)
    }
}