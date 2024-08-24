package com.tosunyan.foodrecipes.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import cafe.adriel.voyager.navigator.tab.Tab
import com.tosunyan.foodrecipes.ui.R
import com.tosunyan.foodrecipes.ui.screens.HomeScreen
import com.tosunyan.foodrecipes.ui.screens.SavedMealsScreen
import com.tosunyan.foodrecipes.ui.screens.SearchScreen
import com.tosunyan.foodrecipes.ui.components.navigationbar.BottomNavigationItem
import com.tosunyan.foodrecipes.ui.theme.painterResource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BottomNavigationViewModel : ViewModel() {

    private val _selectedItem = MutableStateFlow<Tab>(HomeScreen())
    val selectedItem = _selectedItem.asStateFlow()

    private val _navigationItems = MutableStateFlow<List<BottomNavigationItem>>(emptyList())
    val navigationItems = _navigationItems.asStateFlow()

    fun initNavigationItems(context: Context) {
        _navigationItems.update {
            listOf(
                BottomNavigationItem(
                    icon = context.painterResource(id = R.drawable.ic_home),
                    selectedIcon = context.painterResource(id = R.drawable.ic_home_fill),
                    text = context.getString(R.string.navigation_item_home),
                    isSelected = _selectedItem.value is HomeScreen,
                    onClick = { _selectedItem.value = HomeScreen() }
                ),
                BottomNavigationItem(
                    icon = context.painterResource(id = R.drawable.ic_search),
                    selectedIcon = context.painterResource(id = R.drawable.ic_search_fill),
                    text = context.getString(R.string.navigation_item_search),
                    isSelected = _selectedItem.value is SearchScreen,
                    onClick = { _selectedItem.value = SearchScreen() }
                ),
                BottomNavigationItem(
                    icon = context.painterResource(id = R.drawable.ic_bookmark),
                    selectedIcon = context.painterResource(id = R.drawable.ic_bookmark_fill),
                    text = context.getString(R.string.navigation_item_saved_meals),
                    isSelected = _selectedItem.value is SavedMealsScreen,
                    onClick = { _selectedItem.value = SavedMealsScreen() }
                )
            )
        }
    }
}