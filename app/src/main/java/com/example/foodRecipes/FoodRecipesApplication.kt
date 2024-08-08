package com.example.foodRecipes

import android.app.Application
import com.example.foodRecipes.datasource.local.database.DatabaseProvider

class FoodRecipesApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        DatabaseProvider.init(this)
    }
}