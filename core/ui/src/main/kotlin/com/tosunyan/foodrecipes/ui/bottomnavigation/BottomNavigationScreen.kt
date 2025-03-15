package com.tosunyan.foodrecipes.ui.bottomnavigation

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.inconceptlabs.designsystem.components.notification.Notification
import com.inconceptlabs.designsystem.theme.AppTheme
import com.inconceptlabs.designsystem.theme.attributes.KeyColor
import com.tosunyan.foodrecipes.ui.R
import com.tosunyan.foodrecipes.ui.utils.launchAppLinksSettings

class BottomNavigationScreen : Screen {

    @Composable
    override fun Content() {
        val context = LocalContext.current
        val viewModel = viewModel<BottomNavigationViewModel>()

        val screenState by viewModel.screenState.collectAsState()
        val statusBarsPaddingOrDefault =
            if (screenState.showAppLinkNotification) Modifier else Modifier.statusBarsPadding()

        LaunchedEffect(screenState.selectedItem) {
            viewModel.initNavigationItems(context)
        }

        LifecycleEventEffect(event = Lifecycle.Event.ON_START) {
            viewModel.onLifecycleStart(context)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.colorScheme.BG1)
                .then(statusBarsPaddingOrDefault)
        ) {
            AppLinkWarningNotification(
                isVisible = screenState.showAppLinkNotification,
                context = context,
            )

            Box(modifier = Modifier.weight(1f)) {
                TabNavigator(tab = screenState.selectedItem) {
                    it.current = screenState.selectedItem
                    CurrentTab()
                }
            }

            BottomNavigation(
                items = screenState.navigationItems,
                isLabeled = true,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }

    @Composable
    private fun AppLinkWarningNotification(
        isVisible: Boolean,
        context: Context,
    ) {
        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically(),
        ) {
            Notification(
                startIcon = painterResource(id = R.drawable.ic_warning_fill),
                title = stringResource(R.string.warning_app_links_not_verified),
                description = stringResource(R.string.warning_verify_app_links),
                keyColor = KeyColor.ERROR,
                buttonText = stringResource(R.string.verify),
                onButtonClick = context::launchAppLinksSettings,
            )
        }
    }
}