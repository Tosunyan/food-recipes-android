package com.tosunyan.foodrecipes.database

import androidx.room.Room
import org.koin.dsl.module

val DatabaseModule = module {
    single<MealDatabase> {
        Room
            .databaseBuilder(
                context = get(),
                klass = MealDatabase::class.java,
                name = MealDatabase.NAME
            )
            .fallbackToDestructiveMigration(
                dropAllTables = true
            )
            .build()
    }
}