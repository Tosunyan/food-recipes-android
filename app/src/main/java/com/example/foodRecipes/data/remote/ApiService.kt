package com.example.foodRecipes.data.remote

import com.example.foodRecipes.data.remote.responses.CategoryResponse
import com.example.foodRecipes.data.remote.responses.MealResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("search.php")
    fun searchMealByName(@Query("s") name: String?): Call<MealResponse?>?

    @GET("search.php")
    fun searchMealByFirstLetter(@Query("f") firstLetter: Char): Call<MealResponse?>?


    @GET("random.php")
    suspend fun getRandomMeal(): Response<MealResponse>

    @GET("categories.php")
    suspend fun getCategories(): Response<CategoryResponse>

    @GET("list.php?a=list")
    suspend fun getAreas(): Response<MealResponse>


    @GET("filter.php")
    fun filterMealsByCategory(@Query("c") category: String?): Call<MealResponse>

    @GET("filter.php")
    fun filterMealsByArea(@Query("a") area: String?): Call<MealResponse>

    @GET("filter.php")
    fun filterMealsByIngredient(@Query("i") ingredient: String?): Call<MealResponse>


    @GET("lookup.php")
    fun getMealInfo(@Query("i") id: String?): Call<MealResponse>
}