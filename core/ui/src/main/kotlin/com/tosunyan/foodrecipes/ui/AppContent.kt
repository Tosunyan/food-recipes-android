package com.tosunyan.foodrecipes.ui

import android.graphics.Color
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.tosunyan.foodrecipes.ui.bottomnavigation.BottomNavigationScreen
import com.tosunyan.foodrecipes.ui.theme.setThemedContent
import com.tosunyan.foodrecipes.ui.utils.IntentHandler
import org.koin.compose.KoinContext

fun ComponentActivity.setAppContent() {
    setupEdgeToEdge()

    setThemedContent {
        KoinContext {
            Navigator(
                screen = BottomNavigationScreen()
            ) {
                SlideTransition(navigator = it)

                IntentHandler(navigator = it)
            }
        }
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