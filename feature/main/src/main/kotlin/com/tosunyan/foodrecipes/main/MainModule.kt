package com.tosunyan.foodrecipes.main

import com.tosunyan.foodrecipes.main.helpers.MealSavingHelper
import com.tosunyan.foodrecipes.main.helpers.MealSharingHelper
import com.tosunyan.foodrecipes.main.screens.home.HomeViewModel
import com.tosunyan.foodrecipes.main.screens.mealdetails.MealDetailsViewModel
import com.tosunyan.foodrecipes.main.screens.meals.MealsViewModel
import com.tosunyan.foodrecipes.main.screens.savedmeals.SavedMealsViewModel
import com.tosunyan.foodrecipes.main.screens.search.SearchViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val MainModule = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::SearchViewModel)
    viewModelOf(::SavedMealsViewModel)
    viewModelOf(::MealsViewModel)
    viewModelOf(::MealDetailsViewModel)

    singleOf(::MealSharingHelper)
    singleOf(::MealSavingHelper)
}