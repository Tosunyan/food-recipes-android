package com.tosunyan.foodrecipes.ui.bottomnavigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.inconceptlabs.designsystem.theme.AppTheme

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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.colorScheme.BG1)
                .statusBarsPadding()
        ) {
            Box(modifier = Modifier.weight(1f)) {
                TabNavigator(tab = selectedItem) {
                    it.current = selectedItem
                    CurrentTab()
                }
            }

            BottomNavigation(
                items = navigationItems,
                isLabeled = true,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}