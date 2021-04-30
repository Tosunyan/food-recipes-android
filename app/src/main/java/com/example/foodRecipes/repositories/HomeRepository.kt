package com.example.foodRecipes.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.foodRecipes.network.ApiService
import com.example.foodRecipes.responses.CategoryResponse
import com.example.foodRecipes.responses.MealResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class HomeRepository @Inject constructor(private val apiService: ApiService) {

    private val mealResponseMutableLiveData: MutableLiveData<MealResponse?> = MutableLiveData()
    private val areaResponseMutableLiveData: MutableLiveData<MealResponse?> = MutableLiveData()
    private val categoryResponseMutableLiveData: MutableLiveData<CategoryResponse?> = MutableLiveData()

    fun getRandomMeal(): LiveData<MealResponse?> {
        apiService.getRandomMeal()!!.enqueue(object : Callback<MealResponse?> {
            override fun onResponse(call: Call<MealResponse?>, response: Response<MealResponse?>) {
                mealResponseMutableLiveData.value = response.body()
            }

            override fun onFailure(call: Call<MealResponse?>, t: Throwable) {
                mealResponseMutableLiveData.value = null
            }
        })

        return mealResponseMutableLiveData
    }

    fun getCategories(): LiveData<CategoryResponse?> {
        apiService.getCategories().enqueue(object : Callback<CategoryResponse?> {
            override fun onResponse(call: Call<CategoryResponse?>, response: Response<CategoryResponse?>) {
                categoryResponseMutableLiveData.value = response.body()
            }

            override fun onFailure(call: Call<CategoryResponse?>, t: Throwable) {
                categoryResponseMutableLiveData.value = null
            }
        })

        return categoryResponseMutableLiveData
    }

    fun getAreas(): LiveData<MealResponse?> {
        apiService.getAreas()!!.enqueue(object : Callback<MealResponse?> {
            override fun onResponse(call: Call<MealResponse?>, response: Response<MealResponse?>) {
                areaResponseMutableLiveData.value = response.body()
            }

            override fun onFailure(call: Call<MealResponse?>, t: Throwable) {
                areaResponseMutableLiveData.value = null
            }
        })
        return areaResponseMutableLiveData
    }
}