package com.tosunyan.foodrecipes.network.api

import com.tosunyan.foodrecipes.network.data.CategoryDto
import com.tosunyan.foodrecipes.network.data.ListDto
import com.tosunyan.foodrecipes.network.data.MealDetailsDto
import com.tosunyan.foodrecipes.network.data.MealDto
import com.tosunyan.foodrecipes.network.data.RegionDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("random.php")
    suspend fun getRandomMeal(): ListDto<MealDetailsDto>

    @GET("categories.php")
    suspend fun getCategories(): ListDto<CategoryDto>

    @GET("list.php?a=list")
    suspend fun getAreas(): ListDto<RegionDto>


    @GET("lookup.php")
    suspend fun getMealDetails(
        @Query("i") id: String
    ): ListDto<MealDetailsDto>


    @GET("search.php")
    suspend fun searchMealByName(
        @Query("s") name: String
    ): ListDto<MealDetailsDto>

    @GET("search.php")
    suspend fun searchMealByFirstLetter(
        @Query("f") firstLetter: Char
    ): ListDto<MealDetailsDto>


    @GET("filter.php")
    suspend fun filterMealsByCategory(
        @Query("c") category: String
    ): ListDto<MealDto>

    @GET("filter.php")
    suspend fun filterMealsByArea(
        @Query("a") area: String
    ): ListDto<MealDto>
}