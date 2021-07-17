package com.example.foodRecipes.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.foodRecipes.network.ApiService
import com.example.foodRecipes.network.RetrofitClient
import com.example.foodRecipes.responses.MealResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealRepository {

    private val apiService: ApiService = RetrofitClient.getInstance().create(ApiService::class.java)
    private val data = MutableLiveData<MealResponse?>()

    fun filterMealsByCategory(category: String?): LiveData<MealResponse?> {
        apiService.filterMealsByCategory(category).enqueue(object : Callback<MealResponse?> {
            override fun onResponse(call: Call<MealResponse?>, response: Response<MealResponse?>) {
                data.value = response.body()
            }

            override fun onFailure(call: Call<MealResponse?>, t: Throwable) {
                data.value = null
            }
        })
        return data
    }

    fun filterMealsByArea(area: String?): LiveData<MealResponse?> {
        apiService.filterMealsByArea(area).enqueue(object : Callback<MealResponse?> {
            override fun onResponse(call: Call<MealResponse?>, response: Response<MealResponse?>) {
                data.value = response.body()
            }

            override fun onFailure(call: Call<MealResponse?>, t: Throwable) {
                data.value = null
            }
        })
        return data
    }

    fun filterMealsByIngredient(ingredient: String?): LiveData<MealResponse?> {
        apiService.filterMealsByIngredient(ingredient).enqueue(object : Callback<MealResponse?> {
            override fun onResponse(call: Call<MealResponse?>, response: Response<MealResponse?>) {
                data.value = response.body()
            }

            override fun onFailure(call: Call<MealResponse?>, t: Throwable) {
                data.value = null
            }
        })
        return data
    }
}