package com.example.navigation

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import kotlin.reflect.KClass

object NavigationManager {

    private val FragmentManager.backStack: List<FragmentManager.BackStackEntry>
        get() = (0 until backStackEntryCount).map { getBackStackEntryAt(it) }

    fun navigate(
        containerViewId: Int,
        fragmentManager: FragmentManager,
        destinationFragment: KClass<out Fragment>,
        args: Bundle? = null,
    ) {
        val fragment = fragmentManager.instantiateFragment(destinationFragment)
        if (fragment is DialogFragment) {
            showDialog(fragment, fragmentManager, destinationFragment.simpleName, args)
        } else {
            openFragment(containerViewId, fragmentManager, destinationFragment, args)
        }
    }

    fun navigateAndPopBackStackUpTo(
        containerViewId: Int,
        fragmentManager: FragmentManager,
        destinationFragment: KClass<out Fragment>,
        popDestinationFragment: KClass<out Fragment>?,
        inclusive: Boolean,
        args: Bundle? = null,
    ) {
        if (popDestinationFragment == null) {
            popBackStack(fragmentManager)
        } else {
            popBackStackUpTo(fragmentManager, popDestinationFragment, inclusive)
        }
        navigate(containerViewId, fragmentManager, destinationFragment, args)
    }

    fun navigateUp(fragmentManager: FragmentManager) {
        val lastAddedFragment = fragmentManager.fragments.last()
        if (lastAddedFragment is DialogFragment) {
            lastAddedFragment.dismiss()
        } else {
            fragmentManager.popBackStack()
        }
    }

    private fun popBackStackUpTo(
        fragmentManager: FragmentManager,
        fragment: KClass<out Fragment>,
        inclusive: Boolean,
    ) {
        if (fragmentManager.backStack.none { it.name == fragment.simpleName }) return

        for (backStackEntry in fragmentManager.backStack.reversed()) {
            when {
                backStackEntry.name != fragment.simpleName -> {
                    fragmentManager.popBackStack()
                }
                inclusive -> {
                    fragmentManager.popBackStack()
                    break
                }
                else -> {
                    break
                }
            }
        }
    }

    private fun popBackStack(fragmentManager: FragmentManager) {
        repeat(fragmentManager.backStackEntryCount) {
            fragmentManager.popBackStack()
        }
    }

    private fun openFragment(
        containerViewId: Int,
        fragmentManager: FragmentManager,
        destinationFragment: KClass<out Fragment>,
        args: Bundle? = null,
    ) {
        fragmentManager.beginTransaction()
            .replace(containerViewId, destinationFragment.java, args, destinationFragment.simpleName)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .addToBackStack(destinationFragment.simpleName)
            .commit()
    }

    private fun showDialog(
        destinationFragment: DialogFragment,
        fragmentManager: FragmentManager,
        tag: String?,
        args: Bundle? = null,
    ) {
        destinationFragment.arguments = args
        destinationFragment.show(fragmentManager, tag)
    }

    private fun FragmentManager.instantiateFragment(fragmentClass: KClass<out Fragment>): Fragment {
        return fragmentFactory.instantiate(
            ClassLoader.getSystemClassLoader(),
            fragmentClass.java.name
        )
    }
}