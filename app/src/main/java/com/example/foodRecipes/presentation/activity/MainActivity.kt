package com.example.foodRecipes.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.example.foodRecipes.presentation.screens.BottomNavigationScreen
import com.example.foodRecipes.presentation.theme.indication.ScaleIndicationNodeFactory
import com.inconceptlabs.designsystem.utils.ProvideThemedContent

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent()
    }

    private fun setContent() {
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
}