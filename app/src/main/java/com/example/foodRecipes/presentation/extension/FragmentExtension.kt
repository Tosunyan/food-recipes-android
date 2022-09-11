package com.example.foodRecipes.presentation.extension

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.example.foodRecipes.presentation.activities.MainActivity
import com.example.foodRecipes.util.NavigationUtil

val Fragment.mainNavController: NavController
    get() = (requireActivity() as MainActivity).navController

fun Fragment.navigate(@IdRes destinationId: Int, args: Bundle? = null) {
    NavigationUtil.navigate(mainNavController, destinationId, args)
}

fun Fragment.navigateUp() {
    NavigationUtil.navigateUp(mainNavController)
}