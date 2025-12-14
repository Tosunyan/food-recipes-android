package com.tosunyan.foodrecipes.ui

import android.graphics.Color
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.tosunyan.foodrecipes.ui.screens.bottomnavigation.BottomNavigationScreen
import com.tosunyan.foodrecipes.ui.theme.setThemedContent
import com.tosunyan.foodrecipes.ui.utils.IntentHandler
import com.tosunyan.foodrecipes.ui.utils.rememberBackStack

fun ComponentActivity.setAppContent() {
    setupEdgeToEdge()

    setThemedContent {
        val backStack = rememberBackStack()

        IntentHandler(backStack = backStack)

        NavDisplay(
            backStack = backStack.value,
            onBack = backStack.value::removeLastOrNull,
            entryProvider = entryProvider {
                entry<BottomNavigationScreen> {
                    BottomNavigationScreen()
                }
            }
        )
    }
}

private fun ComponentActivity.setupEdgeToEdge() {
    enableEdgeToEdge(
        statusBarStyle = SystemBarStyle.light(
            scrim = Color.TRANSPARENT,
            darkScrim = Color.TRANSPARENT,
        ),
        navigationBarStyle = SystemBarStyle.light(
            scrim = Color.TRANSPARENT,
            darkScrim = Color.TRANSPARENT,
        ),
    )
}