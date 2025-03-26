package com.tosunyan.foodrecipes.ui.screens.bottomnavigation

import cafe.adriel.voyager.navigator.tab.Tab

data class NavigationScreenState(
    val selectedItem: Tab,
    val navigationItems: List<BottomNavigationItem> = emptyList(),

    val appLinksVerified: Boolean = true,
) {

    val showAppLinkNotification: Boolean
        get() = !appLinksVerified
}