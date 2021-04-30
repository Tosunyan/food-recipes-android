package com.example.foodRecipes.network

import com.example.foodRecipes.responses.CategoryResponse
import com.example.foodRecipes.responses.MealResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("search.php")
    fun searchMealByName(@Query("s") name: String?): Call<MealResponse?>?

    @GET("search.php")
    fun searchMealByFirstLetter(@Query("f") firstLetter: Char): Call<MealResponse?>?


    @GET("random.php")
    fun getRandomMeal(): Call<MealResponse?>?

    @GET("categories.php")
    fun getCategories(): Call<CategoryResponse>

    @GET("list.php?a=list")
    fun getAreas(): Call<MealResponse?>?


    @GET("filter.php")
    fun filterMealsByCategory(@Query("c") category: String?): Call<MealResponse?>?

    @GET("filter.php")
    fun filterMealsByArea(@Query("a") area: String?): Call<MealResponse?>?

    @GET("filter.php")
    fun filterMealsByIngredient(@Query("i") ingredient: String?): Call<MealResponse?>?


    @GET("lookup.php")
    fun getMealInfo(@Query("i") id: String?): Call<MealResponse?>?
}