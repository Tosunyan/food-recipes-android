package com.tosunyan.foodrecipes.ui.screens

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.tosunyan.foodrecipes.ui.components.navigationbar.BottomNavigation
import com.tosunyan.foodrecipes.ui.components.notification.Notification
import com.tosunyan.foodrecipes.ui.helpers.NotificationManager
import com.tosunyan.foodrecipes.ui.viewmodel.BottomNavigationViewModel

class BottomNavigationScreen : Screen {

    @Composable
    override fun Content() {
        val context = LocalContext.current
        val viewModel = viewModel<BottomNavigationViewModel>()

        val selectedItem by viewModel.selectedItem.collectAsState()
        val navigationItems by viewModel.navigationItems.collectAsState()

        LaunchedEffect(selectedItem) {
            viewModel.initNavigationItems(context)
        }

        Column(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.weight(1f)) {
                TabNavigator(tab = selectedItem) {
                    it.current = selectedItem
                    CurrentTab()
                }

                Notification()
            }

            BottomNavigation(
                items = navigationItems,
                isLabeled = true,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }

    @Composable
    private fun BoxScope.Notification() {
        val notificationState by NotificationManager.notificationData.collectAsStateWithLifecycle()
        val isNotificationVisible by NotificationManager.isNotificationVisible.collectAsStateWithLifecycle()

        androidx.compose.animation.AnimatedVisibility(
            visible = isNotificationVisible,
            enter = fadeIn() + slideInVertically(
                animationSpec = spring(stiffness = Spring.StiffnessHigh),
                initialOffsetY = { it / 2 }
            ),
            exit = fadeOut() + slideOutVertically(
                animationSpec = spring(stiffness = Spring.StiffnessHigh),
                targetOffsetY = { it / 2 }
            ),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(20.dp)
        ) {
            Notification(
                data = notificationState ?: return@AnimatedVisibility
            )
        }
    }
}