package com.tosunyan.foodrecipes.ui

import androidx.activity.ComponentActivity
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.tosunyan.foodrecipes.ui.screens.BottomNavigationScreen
import com.tosunyan.foodrecipes.ui.theme.indication.ScaleIndicationNodeFactory
import com.inconceptlabs.designsystem.utils.ProvideThemedContent

fun ComponentActivity.setAppContent() {
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