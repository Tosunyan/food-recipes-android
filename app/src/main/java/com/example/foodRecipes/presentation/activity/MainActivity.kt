package com.example.foodRecipes.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.foodRecipes.R

class MainActivity : AppCompatActivity() {

    val navController: NavController by lazy {
        (supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment).navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
    }
}