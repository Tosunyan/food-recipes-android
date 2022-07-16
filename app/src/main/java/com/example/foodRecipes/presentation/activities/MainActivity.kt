package com.example.foodRecipes.presentation.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)


        navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHostFragment.navController

        initNavigation()
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