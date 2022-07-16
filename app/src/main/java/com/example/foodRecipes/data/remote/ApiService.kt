package com.example.foodRecipes.data.remote

import com.example.foodRecipes.data.remote.responses.CategoryResponse
import com.example.foodRecipes.data.remote.responses.MealResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("random.php")
    suspend fun getRandomMeal(): Response<MealResponse>

    @GET("categories.php")
    suspend fun getCategories(): Response<CategoryResponse>

    @GET("list.php?a=list")
    suspend fun getAreas(): Response<MealResponse>


    @GET("lookup.php")
    suspend fun getMealDetails(@Query("i") id: String?): Response<MealResponse>


    @GET("search.php")
    suspend fun searchMealByName(@Query("s") name: String?): Response<MealResponse>

    @GET("search.php")
    suspend fun searchMealByFirstLetter(@Query("f") firstLetter: Char): Response<MealResponse>


    @GET("filter.php")
    suspend fun filterMealsByCategory(@Query("c") category: String?): Response<MealResponse>

    @GET("filter.php")
    suspend fun filterMealsByArea(@Query("a") area: String?): Response<MealResponse>

    @GET("filter.php")
    suspend fun filterMealsByIngredient(@Query("i") ingredient: String?): Response<MealResponse>
}