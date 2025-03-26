package com.tosunyan.foodrecipes.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inconceptlabs.designsystem.components.emptyitem.EmptyItemData
import com.tosunyan.foodrecipes.common.coroutines.WhileSubscribedOrRetained
import com.tosunyan.foodrecipes.common.utils.replace
import com.tosunyan.foodrecipes.data.repositories.SearchRepository
import com.tosunyan.foodrecipes.model.MealDetailsModel
import com.tosunyan.foodrecipes.ui.R
import com.tosunyan.foodrecipes.ui.helpers.MealSavingHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@Suppress("OPT_IN_USAGE")
class SearchViewModel(
    private val repository: SearchRepository,
    private val mealSavingHelper: MealSavingHelper,
) : ViewModel() {

    private val _meals = MutableStateFlow<List<MealDetailsModel>>(emptyList())
    val meals = _meals.asStateFlow()

    private val _searchInput = MutableStateFlow("")
    val searchInput = _searchInput.asStateFlow()

    val emptyItemData: StateFlow<EmptyItemData?> =
        combine(_meals, _searchInput) { items, input ->
            when {
                input.isBlank() -> blankSearchEmptyItemData
                items.isEmpty() -> noResultsEmptyItemData
                else -> null
            }
        }.stateIn(
            scope = viewModelScope,
            started = WhileSubscribedOrRetained,
            initialValue = blankSearchEmptyItemData
        )

    init {
        _searchInput
            .debounce(SEARCH_DEBOUNCE)
            .filter(String::isNotBlank)
            .onEach(::searchForMeals)
            .launchIn(viewModelScope)
    }

    fun onSearchInputChange(text: String = "") {
        if (text.isBlank()) {
            _meals.value = emptyList()
        }

        _searchInput.value = text
    }

    fun onSearchCloseClick() {
        onSearchInputChange()
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
                .onSuccess { _meals.value = it }
        }
    }

    companion object {

        private const val SEARCH_DEBOUNCE = 400L

        private val blankSearchEmptyItemData = EmptyItemData(
            iconId = R.drawable.ic_search,
            titleId = R.string.search_get_started_title,
            descriptionId = R.string.search_get_started_description,
        )

        private val noResultsEmptyItemData = EmptyItemData(
            iconId = R.drawable.ic_file_error,
            titleId = R.string.search_no_results_title,
            descriptionId = R.string.search_no_results_description,
        )
    }
}