package com.tosunyan.foodrecipes.network.api

import com.tosunyan.foodrecipes.common.utils.configuredJson
import com.tosunyan.foodrecipes.network.BuildConfig
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.create

object Api {

    private const val BASE_URL: String =
        "${BuildConfig.MEAL_API_BASE_URL}/${BuildConfig.MEAL_API_KEY}/"

    val client: ApiService by lazy {
        retrofit.create()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(converterFactory)
            .build()
    }

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(LoggingInterceptor)
            .build()
    }

    private val converterFactory by lazy {
        val contentType = "application/json".toMediaType()
        configuredJson.asConverterFactory(contentType)
    }
}