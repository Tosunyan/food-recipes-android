package com.example.foodRecipes.util

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.example.foodRecipes.R

object NavigationUtil {

    fun navigate(
        navController: NavController,
        @IdRes resId: Int,
        args: Bundle? = null,
    ) {
        navController.navigate(resId, args, getNavOptions())
    }

    fun navigateUp(navController: NavController) {
        navController.navigateUp()
    }

    private fun getNavOptions(
        destinationId: Int? = null,
        inclusive: Boolean = false,
    ): NavOptions = NavOptions.Builder().apply {
        setEnterAnim(R.anim.anim_fragment_enter)
        setExitAnim(R.anim.anim_fragment_exit)
        setPopEnterAnim(R.anim.anim_fragment_enter)
        setPopExitAnim(R.anim.anim_fragment_exit)

        if (destinationId != null) {
            setPopUpTo(destinationId, inclusive)
        }
    }.build()
}