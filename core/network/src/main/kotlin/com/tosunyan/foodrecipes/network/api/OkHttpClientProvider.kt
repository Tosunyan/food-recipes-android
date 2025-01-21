package com.tosunyan.foodrecipes.network.api

import okhttp3.OkHttpClient

class OkHttpClientProvider {

    val value = OkHttpClient.Builder()
        .addInterceptor(LoggingInterceptor)
        .build()
}