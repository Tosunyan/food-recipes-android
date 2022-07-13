package com.example.foodRecipes.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.example.foodRecipes.domain.repositories.SearchRepository
import androidx.lifecycle.LiveData
import com.example.foodRecipes.data.remote.responses.MealResponse

class SearchViewModel : ViewModel() {

    private val repository: SearchRepository = SearchRepository()

    fun getMealsByFirstLetter(letter: Char): LiveData<MealResponse?> =
        repository.searchByFirstLetter(letter)

    fun getMealsByName(name: String?): LiveData<MealResponse?> =
        repository.searchByName(name)
}