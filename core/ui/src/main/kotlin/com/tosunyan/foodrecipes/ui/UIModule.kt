package com.tosunyan.foodrecipes.ui

import com.tosunyan.foodrecipes.ui.helpers.MealSavingHelper
import com.tosunyan.foodrecipes.ui.helpers.MealSharingHelper
import com.tosunyan.foodrecipes.ui.mealdetails.MealDetailsViewModel
import com.tosunyan.foodrecipes.ui.viewmodel.HomeViewModel
import com.tosunyan.foodrecipes.ui.viewmodel.MealsViewModel
import com.tosunyan.foodrecipes.ui.viewmodel.SavedMealsViewModel
import com.tosunyan.foodrecipes.ui.viewmodel.SearchViewModel
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
}