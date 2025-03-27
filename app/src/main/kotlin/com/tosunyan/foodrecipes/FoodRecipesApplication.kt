package com.tosunyan.foodrecipes

import android.app.Application
import com.google.firebase.Firebase
import com.google.firebase.crashlytics.crashlytics
import com.google.firebase.initialize
import com.tosunyan.foodrecipes.ui.UIModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class FoodRecipesApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initCrashlytics()
        initKoin()
    }

    private fun initCrashlytics() {
        with(Firebase) {
            initialize(this@FoodRecipesApplication)
            crashlytics.isCrashlyticsCollectionEnabled = true
        }
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@FoodRecipesApplication)

            modules(UIModule)
        }
    }
}