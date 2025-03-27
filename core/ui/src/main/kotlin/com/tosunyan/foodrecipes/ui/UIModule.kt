package com.tosunyan.foodrecipes.ui

import com.tosunyan.foodrecipes.common.coroutines.CommonModule
import com.tosunyan.foodrecipes.data.repositories.RepositoryModule
import com.tosunyan.foodrecipes.ui.helpers.MealSavingHelper
import com.tosunyan.foodrecipes.ui.helpers.MealSharingHelper
import com.tosunyan.foodrecipes.ui.screens.home.HomeViewModel
import com.tosunyan.foodrecipes.ui.screens.mealdetails.MealDetailsViewModel
import com.tosunyan.foodrecipes.ui.screens.meals.MealsViewModel
import com.tosunyan.foodrecipes.ui.screens.savedmeals.SavedMealsViewModel
import com.tosunyan.foodrecipes.ui.screens.search.SearchViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val UIModule = module {
    singleOf(::MealSavingHelper)
    singleOf(::MealSharingHelper)

    viewModelOf(::HomeViewModel)
    viewModelOf(::SearchViewModel)
    viewModelOf(::SavedMealsViewModel)
    viewModelOf(::MealsViewModel)
    viewModelOf(::MealDetailsViewModel)

    includes(
        CommonModule,
        RepositoryModule,
    )
}