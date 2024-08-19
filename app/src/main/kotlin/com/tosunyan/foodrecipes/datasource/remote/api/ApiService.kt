package com.tosunyan.foodrecipes.datasource.remote.api

import com.tosunyan.foodrecipes.datasource.remote.data.CategoryDto
import com.tosunyan.foodrecipes.datasource.remote.data.ListDto
import com.tosunyan.foodrecipes.datasource.remote.data.MealDetailsDto
import com.tosunyan.foodrecipes.datasource.remote.data.MealDto
import com.tosunyan.foodrecipes.datasource.remote.data.RegionDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("random.php")
    suspend fun getRandomMeal(): Response<ListDto<MealDetailsDto>>

    @GET("categories.php")
    suspend fun getCategories(): Response<ListDto<CategoryDto>>

    @GET("list.php?a=list")
    suspend fun getAreas(): Response<ListDto<RegionDto>>


    @GET("lookup.php")
    suspend fun getMealDetails(
        @Query("i") id: String
    ): Response<ListDto<MealDetailsDto>>


    @GET("search.php")
    suspend fun searchMealByName(
        @Query("s") name: String
    ): Response<ListDto<MealDetailsDto>>

    @GET("search.php")
    suspend fun searchMealByFirstLetter(
        @Query("f") firstLetter: Char
    ): Response<ListDto<MealDetailsDto>>


    @GET("filter.php")
    suspend fun filterMealsByCategory(
        @Query("c") category: String
    ): Response<ListDto<MealDto>>

    @GET("filter.php")
    suspend fun filterMealsByArea(
        @Query("a") area: String
    ): Response<ListDto<MealDto>>
}