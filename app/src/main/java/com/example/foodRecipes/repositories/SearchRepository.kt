package com.example.foodRecipes.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.foodRecipes.network.ApiService
import com.example.foodRecipes.network.RetrofitClient
import com.example.foodRecipes.responses.MealResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchRepository {

    private val data: MutableLiveData<MealResponse?> = MutableLiveData()
    private val apiService: ApiService = RetrofitClient.retrofit.create(ApiService::class.java)

    fun searchMealsByFirstLetter(letter: Char): LiveData<MealResponse?> {
        apiService.searchMealByFirstLetter(letter)!!.enqueue(object : Callback<MealResponse?> {
            override fun onResponse(call: Call<MealResponse?>, response: Response<MealResponse?>) {
                data.value = response.body()
            }

            override fun onFailure(call: Call<MealResponse?>, t: Throwable) {
                data.value = null
            }
        })
        return data
    }

    fun searchMealsByName(name: String?): LiveData<MealResponse?> {
        apiService.searchMealByName(name)!!.enqueue(object : Callback<MealResponse?> {
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