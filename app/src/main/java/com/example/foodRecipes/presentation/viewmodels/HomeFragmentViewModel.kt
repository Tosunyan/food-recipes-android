package com.example.foodRecipes.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodRecipes.data.remote.ApiService
import com.example.foodRecipes.data.remote.RetrofitClient
import com.example.foodRecipes.domain.repositories.HomeRepository
import com.example.foodRecipes.data.remote.responses.CategoryResponse
import com.example.foodRecipes.data.remote.responses.MealResponse
import kotlinx.coroutines.*

class HomeFragmentViewModel : ViewModel() {

    private val repository = HomeRepository(RetrofitClient.getInstance().create(ApiService::class.java))

    val randomMealData = MutableLiveData<MealResponse>()
    val categoryData = MutableLiveData<CategoryResponse>()
    val areaData = MutableLiveData<MealResponse>()

    val loading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()

    private var job: Job? = null


    override fun onCleared() {
        super.onCleared()

        job?.cancel()
    }

    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }

    fun getRandomMeal() {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getRandomMeal()

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    randomMealData.postValue(response.body())
                    loading.value = false
                } else onError("Error: ${response.message()}")
            }
        }
    }

    fun getCategories() {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getCategories()

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    categoryData.postValue(response.body())
                    loading.value = false
                } else onError("Error: ${response.message()}")
            }
        }
    }

    fun getAreas() {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getAreas()

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    areaData.postValue(response.body())
                    loading.value = false
                } else onError("Error: ${response.message()}")
            }
        }
    }
}