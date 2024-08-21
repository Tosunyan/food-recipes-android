package com.tosunyan.foodrecipes

import android.app.Application
import com.tosunyan.foodrecipes.database.DatabaseProvider

class FoodRecipesApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        DatabaseProvider.init(this)
    }
}