package com.tosunyan.foodrecipes.data.repositories

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val RepositoryModule = module {
    singleOf(::HomeRepository)
    singleOf(::MealRepository)
    singleOf(::SearchRepository)
}