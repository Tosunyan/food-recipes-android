package com.tosunyan.foodrecipes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tosunyan.foodrecipes.ui.setAppContent

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setAppContent()
    }
}