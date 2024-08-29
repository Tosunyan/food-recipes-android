package com.tosunyan.foodrecipes.ui

import android.graphics.Color
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.inconceptlabs.designsystem.utils.ProvideThemedContent
import com.tosunyan.foodrecipes.ui.screens.BottomNavigationScreen
import com.tosunyan.foodrecipes.ui.theme.indication.ScaleIndicationNodeFactory

fun ComponentActivity.setAppContent() {
    setupEdgeToEdge()

    ProvideThemedContent(
        indication = ScaleIndicationNodeFactory
    ) {
        Navigator(
            screen = BottomNavigationScreen()
        ) {
            SlideTransition(navigator = it)
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