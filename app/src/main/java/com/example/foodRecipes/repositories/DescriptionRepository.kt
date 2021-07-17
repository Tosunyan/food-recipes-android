package com.example.foodRecipes.repositories

import com.example.foodRecipes.network.ApiService
import androidx.lifecycle.MutableLiveData
import com.example.foodRecipes.responses.MealResponse
import androidx.lifecycle.LiveData
import com.example.foodRecipes.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DescriptionRepository {

    private val apiService: ApiService = RetrofitClient.getInstance().create(ApiService::class.java)
    private val data: MutableLiveData<MealResponse> = MutableLiveData()

    fun getMealInfo(id: String?): LiveData<MealResponse> {

        apiService.getMealInfo(id)!!.enqueue(object : Callback<MealResponse?> {
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