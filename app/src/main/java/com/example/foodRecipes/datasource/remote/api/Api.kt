package com.example.foodRecipes.datasource.remote.api

import com.example.foodRecipes.util.logApiRequest
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object Api {

    private const val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"

    val client: ApiService by lazy {
        retrofit.create()
    }

    private val retrofit: Retrofit by lazy {
        val gson = GsonBuilder().setLenient().create()

        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(::intercept)
            .build()
    }

    private fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestBuilder = request.newBuilder()

        logApiRequest(request)

        return chain.proceed(requestBuilder.build())
    }
}