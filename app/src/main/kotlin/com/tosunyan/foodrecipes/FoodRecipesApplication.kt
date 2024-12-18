package com.tosunyan.foodrecipes

import android.app.Application
import com.tosunyan.foodrecipes.common.coroutines.CommonModule
import com.tosunyan.foodrecipes.data.repositories.RepositoryModule
import com.tosunyan.foodrecipes.database.DatabaseModule
import com.tosunyan.foodrecipes.ui.UIModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class FoodRecipesApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@FoodRecipesApplication)

            modules(
                CommonModule,
                DatabaseModule,
                RepositoryModule,
                UIModule,
            )
        }
    }
}