package com.example.foodRecipes.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import cafe.adriel.voyager.navigator.tab.Tab
import com.example.foodRecipes.R
import com.example.foodRecipes.presentation.screens.HomeScreen
import com.example.foodRecipes.presentation.screens.SavedMealsScreen
import com.example.foodRecipes.presentation.screens.SearchScreen
import com.example.foodRecipes.presentation.theme.components.BottomNavigationItem
import com.example.foodRecipes.presentation.theme.painterResource
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
                    text = context.getString(R.string.navigation_item_home),
                    isSelected = _selectedItem.value is HomeScreen,
                    onClick = { _selectedItem.value = HomeScreen() }
                ),
                BottomNavigationItem(
                    icon = context.painterResource(id = R.drawable.ic_search),
                    text = context.getString(R.string.navigation_item_search),
                    isSelected = _selectedItem.value is SearchScreen,
                    onClick = { _selectedItem.value = SearchScreen() }
                ),
                BottomNavigationItem(
                    icon = context.painterResource(id = R.drawable.ic_like),
                    text = context.getString(R.string.navigation_item_saved_meals),
                    isSelected = _selectedItem.value is SavedMealsScreen,
                    onClick = { _selectedItem.value = SavedMealsScreen() }
                )
            )
        }
    }
}