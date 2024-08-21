package com.tosunyan.foodrecipes.network.api

import com.tosunyan.foodrecipes.common.utils.configuredJson
import com.tosunyan.foodrecipes.network.utils.logApiRequest
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.create

object Api {

    private const val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"

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
            .addInterceptor(::intercept)
            .build()
    }

    private val converterFactory by lazy {
        val contentType = "application/json".toMediaType()
        configuredJson.asConverterFactory(contentType)
    }

    private fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestBuilder = request.newBuilder()

        logApiRequest(request)

        return chain.proceed(requestBuilder.build())
    }
}