package com.tosunyan.foodrecipes.network.api.client.retrofit

import com.tosunyan.foodrecipes.network.api.BaseUrl
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit

class RetrofitProvider(
    baseUrl: BaseUrl,
    httpClient: OkHttpClient,
    converterFactory: Converter.Factory,
) {

    val value: Retrofit = Retrofit.Builder()
        .client(httpClient)
        .baseUrl(baseUrl.value)
        .addConverterFactory(converterFactory)
        .build()
}