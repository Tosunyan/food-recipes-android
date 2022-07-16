package com.example.foodRecipes.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object Api {

    private const val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"

    val client: ApiService by lazy {
        retrofit.create()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}