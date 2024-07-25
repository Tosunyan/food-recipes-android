package com.example.foodRecipes.presentation.extension

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.foodRecipes.R
import com.example.navigation.NavigationManager
import com.google.android.material.snackbar.Snackbar
import kotlin.reflect.KClass

inline val Fragment.activityFragmentManager: FragmentManager
    get() = requireActivity().supportFragmentManager

fun Fragment.navigate(
    destinationFragment: KClass<out Fragment>,
    args: Bundle? = null,
    containerViewId: Int = R.id.main_fragment_container,
    fragmentManager: FragmentManager = activityFragmentManager,
) {
    NavigationManager.navigate(containerViewId, fragmentManager, destinationFragment, args)
}

fun Fragment.navigateUp() {
    NavigationManager.navigateUp(activityFragmentManager)
}

fun Fragment.showSnackBar(
    text: String,
    length: Int = Snackbar.LENGTH_SHORT
) {
    val view = view ?: return
    Snackbar.make(view, text, length).show()
}