package com.example.foodRecipes.presentation.extension

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodRecipes.datasource.remote.api.ApiResponse
import kotlinx.coroutines.launch

fun <T> ViewModel.convertToLiveData(response: suspend () -> ApiResponse<T>): LiveData<ApiResponse<T>> {
    val liveData = MutableLiveData<ApiResponse<T>>()

    viewModelScope.launch {
        liveData.value = response.invoke()
    }

    return liveData
}