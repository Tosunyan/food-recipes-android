package com.example.foodRecipes.datasource.local.database

import android.app.Application
import androidx.room.Room

object DatabaseProvider {

    private const val DATABASE_NAME = "food-recipes-database"

    private var _instance: MealDatabase? = null

    val instance: MealDatabase
        get() = _instance ?: error("Database not initialized. Call `DatabaseProvider.init(context)` first.")

    fun init(application: Application) {
        if (_instance == null) {
            _instance = buildDatabase(application)
        }
    }

    private fun buildDatabase(application: Application): MealDatabase {
        return Room
            .databaseBuilder(
                application,
                MealDatabase::class.java,
                DATABASE_NAME
            )
            .fallbackToDestructiveMigration()
            .build()
    }
}