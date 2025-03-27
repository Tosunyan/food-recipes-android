package com.tosunyan.foodrecipes.data.repositories

import com.tosunyan.foodrecipes.database.DatabaseModule
import com.tosunyan.foodrecipes.network.NetworkModule
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val RepositoryModule = module {
    singleOf(::HomeRepository)
    singleOf(::MealRepository)
    singleOf(::SearchRepository)

    includes(
        DatabaseModule,
        NetworkModule,
    )
}