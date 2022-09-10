package com.example.foodRecipes.presentation.extension

import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.example.foodRecipes.presentation.activities.MainActivity

val Fragment.mainNavController: NavController
    get() = (requireActivity() as MainActivity).navController