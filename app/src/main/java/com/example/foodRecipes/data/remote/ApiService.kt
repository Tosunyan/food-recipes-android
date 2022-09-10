package com.example.foodRecipes.data.remote

import com.example.foodRecipes.data.remote.data.CategoriesDto
import com.example.foodRecipes.data.remote.data.MealsDto
import com.example.foodRecipes.data.remote.data.RegionsDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("random.php")
    suspend fun getRandomMeal(): Response<MealsDto>

    @GET("categories.php")
    suspend fun getCategories(): Response<CategoriesDto>

    @GET("list.php?a=list")
    suspend fun getAreas(): Response<RegionsDto>


    @GET("lookup.php")
    suspend fun getMealDetails(@Query("i") id: String?): Response<MealsDto>


    @GET("search.php")
    suspend fun searchMealByName(@Query("s") name: String?): Response<MealsDto>

    @GET("search.php")
    suspend fun searchMealByFirstLetter(@Query("f") firstLetter: Char): Response<MealsDto>


    @GET("filter.php")
    suspend fun filterMealsByCategory(@Query("c") category: String?): Response<MealsDto>

    @GET("filter.php")
    suspend fun filterMealsByArea(@Query("a") area: String?): Response<MealsDto>

    @GET("filter.php")
    suspend fun filterMealsByIngredient(@Query("i") ingredient: String?): Response<MealsDto>
}