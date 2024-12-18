package com.tosunyan.foodrecipes.database

import androidx.room.Room
import org.koin.dsl.module

val DatabaseModule = module {
    single<MealDatabase> {
        Room
            .databaseBuilder(
                get(),
                MealDatabase::class.java,
                MealDatabase.NAME
            )
            .fallbackToDestructiveMigration()
            .build()
    }
}