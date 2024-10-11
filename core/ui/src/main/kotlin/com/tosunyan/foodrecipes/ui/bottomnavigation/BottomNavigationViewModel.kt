package com.tosunyan.foodrecipes.ui.bottomnavigation

import android.content.Context
import androidx.lifecycle.ViewModel
import cafe.adriel.voyager.navigator.tab.Tab
import com.tosunyan.foodrecipes.ui.R
import com.tosunyan.foodrecipes.ui.screens.HomeScreen
import com.tosunyan.foodrecipes.ui.screens.SavedMealsScreen
import com.tosunyan.foodrecipes.ui.screens.SearchScreen
import com.tosunyan.foodrecipes.ui.theme.painterResource
import com.tosunyan.foodrecipes.ui.utils.areAppLinksVerified
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BottomNavigationViewModel : ViewModel() {

    private val defaultScreenState = NavigationScreenState(selectedItem = HomeScreen())
    private val _screenState = MutableStateFlow(defaultScreenState)
    val screenState = _screenState.asStateFlow()

    fun onLifecycleStart(context: Context) {
        verifyAppLinkNotification(context)
    }

    fun initNavigationItems(context: Context) {
        _screenState.update {
            it.copy(navigationItems = listOf(
                BottomNavigationItem(
                    icon = context.painterResource(id = R.drawable.ic_home),
                    selectedIcon = context.painterResource(id = R.drawable.ic_home_fill),
                    text = context.getString(R.string.navigation_item_home),
                    isSelected = it.selectedItem is HomeScreen,
                    onClick = { updateSelectedItem(HomeScreen()) }
                ),
                BottomNavigationItem(
                    icon = context.painterResource(id = R.drawable.ic_search),
                    selectedIcon = context.painterResource(id = R.drawable.ic_search_fill),
                    text = context.getString(R.string.navigation_item_search),
                    isSelected = it.selectedItem is SearchScreen,
                    onClick = { updateSelectedItem(SearchScreen()) }
                ),
                BottomNavigationItem(
                    icon = context.painterResource(id = R.drawable.ic_bookmark),
                    selectedIcon = context.painterResource(id = R.drawable.ic_bookmark_fill),
                    text = context.getString(R.string.navigation_item_saved_meals),
                    isSelected = it.selectedItem is SavedMealsScreen,
                    onClick = { updateSelectedItem(SavedMealsScreen()) }
                )
            ))
        }
    }

    private fun updateSelectedItem(item: Tab) {
        _screenState.update {
            it.copy(selectedItem = item)
        }
    }

    private fun verifyAppLinkNotification(context: Context) {
        _screenState.update {
            it.copy(appLinksVerified = context.areAppLinksVerified())
        }
    }
}