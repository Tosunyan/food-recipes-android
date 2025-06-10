package com.tosunyan.foodrecipes.ui

import android.graphics.Color
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.Lifecycle.Event.*
import androidx.lifecycle.eventFlow
import androidx.lifecycle.lifecycleScope
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.tosunyan.foodrecipes.ui.deeplink.DeepLinkProducer
import com.tosunyan.foodrecipes.ui.screens.bottomnavigation.BottomNavigationScreen
import com.tosunyan.foodrecipes.ui.theme.setThemedContent
import com.tosunyan.foodrecipes.ui.utils.IntentHandler
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.compose.KoinContext

fun ComponentActivity.setAppContent() {
    setupEdgeToEdge()

    DeepLinkProducer(activity = this).let(lifecycle::addObserver)

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

private fun ComponentActivity.lifecycleHandler(
    onCreate: () -> Unit = { },
    onStart: () -> Unit = { },
    onResume: () -> Unit = { },
    onPause: () -> Unit = { },
    onStop: () -> Unit = { },
    onDestroy: () -> Unit = { },
    onLifecycleChange: () -> Unit = { },
) {
    lifecycle.eventFlow
        .onEach {
            when (it) {
                ON_CREATE -> onCreate()
                ON_START -> onStart()
                ON_RESUME -> onResume()
                ON_PAUSE -> onPause()
                ON_STOP -> onStop()
                ON_DESTROY -> onDestroy()
                ON_ANY -> onLifecycleChange()
            }
        }
        .launchIn(lifecycleScope)
}