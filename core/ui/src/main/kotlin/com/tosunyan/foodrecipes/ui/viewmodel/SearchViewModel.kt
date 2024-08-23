package com.tosunyan.foodrecipes.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tosunyan.foodrecipes.common.utils.replace
import com.tosunyan.foodrecipes.data.repositories.MealRepository
import com.tosunyan.foodrecipes.data.repositories.SearchRepository
import com.tosunyan.foodrecipes.model.MealDetailsModel
import com.tosunyan.foodrecipes.network.api.onSuccess
import com.tosunyan.foodrecipes.ui.helpers.MealSavingHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@Suppress("OPT_IN_USAGE")
class SearchViewModel(
    private val repository: SearchRepository = SearchRepository(),
    private val mealRepository: MealRepository = MealRepository(),
    private val mealSavingHelper: MealSavingHelper = MealSavingHelper(mealRepository)
) : ViewModel() {

    private val _meals = MutableStateFlow<List<MealDetailsModel>>(emptyList())
    val meals = _meals.asStateFlow()

    private val searchInput = MutableStateFlow("")

    init {
        searchInput
            .debounce(SEARCH_DEBOUNCE)
            .filter(String::isNotBlank)
            .onEach(::searchForMeals)
            .launchIn(viewModelScope)
    }

    fun onSearchInputChange(text: String = "") {
        if (text.isBlank()) {
            _meals.value = emptyList()
            return
        }

        searchInput.value = text
    }

    fun onSaveIconClick(meal: MealDetailsModel) {
        viewModelScope.launch {
            mealSavingHelper.toggleSavedState(meal) { isSaved ->
                _meals.update { meals ->
                    meals.replace(meal) { it.copy(isSaved = isSaved) }
                }
            }
        }
    }

    private fun searchForMeals(text: String) {
        viewModelScope.launch {
            repository
                .searchMeals(text)
                .onSuccess { _meals.value = this }
        }
    }

    companion object {

        private const val SEARCH_DEBOUNCE = 400L
    }
}