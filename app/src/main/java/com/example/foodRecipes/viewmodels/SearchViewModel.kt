package com.example.foodRecipes.viewmodels

import androidx.lifecycle.ViewModel
import com.example.foodRecipes.repositories.SearchRepository
import androidx.lifecycle.LiveData
import com.example.foodRecipes.responses.MealResponse

class SearchViewModel : ViewModel() {

    private val repository: SearchRepository = SearchRepository()

    fun getMealsByFirstLetter(letter: Char): LiveData<MealResponse?> =
        repository.searchMealsByFirstLetter(letter)

    fun getMealsByName(name: String?): LiveData<MealResponse?> =
        repository.searchMealsByName(name)
}