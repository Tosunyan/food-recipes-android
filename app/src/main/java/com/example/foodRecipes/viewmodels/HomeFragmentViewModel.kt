package com.example.foodRecipes.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.foodRecipes.repositories.HomeRepository
import com.example.foodRecipes.responses.CategoryResponse
import com.example.foodRecipes.responses.MealResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(private val repository: HomeRepository) : ViewModel() {

    fun getRandomMeal(): LiveData<MealResponse?> = repository.getRandomMeal()

    fun getCategories(): LiveData<CategoryResponse?> = repository.getCategories()

    fun getAreas(): LiveData<MealResponse?> = repository.getAreas()
}