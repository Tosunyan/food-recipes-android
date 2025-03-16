package com.tosunyan.foodrecipes.ui

import android.graphics.Color
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.CompositionLocalProvider
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.tosunyan.foodrecipes.ui.bottomnavigation.BottomNavigationScreen
import com.tosunyan.foodrecipes.ui.theme.LocalAnimatedContentScope
import com.tosunyan.foodrecipes.ui.theme.LocalSharedTransitionScope
import com.tosunyan.foodrecipes.ui.theme.setThemedContent
import com.tosunyan.foodrecipes.ui.utils.IntentHandler
import org.koin.compose.KoinContext

@OptIn(ExperimentalSharedTransitionApi::class)
fun ComponentActivity.setAppContent() {
    setupEdgeToEdge()

    setThemedContent {
        KoinContext {
            Navigator(
                screen = BottomNavigationScreen()
            ) { navigator ->
                SharedTransitionLayout {
                    AnimatedContent(
                        targetState = navigator,
                    ) {
                        CompositionLocalProvider(
                            LocalSharedTransitionScope provides this@SharedTransitionLayout,
                            LocalAnimatedContentScope provides this@AnimatedContent,
                        ) {
                            SlideTransition(navigator = it)
                        }
                    }
                }

                IntentHandler(navigator = navigator)
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