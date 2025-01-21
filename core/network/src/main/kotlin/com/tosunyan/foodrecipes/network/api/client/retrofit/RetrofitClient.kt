package com.tosunyan.foodrecipes.network.api.client.retrofit

import com.tosunyan.foodrecipes.network.api.ApiService
import retrofit2.Retrofit
import retrofit2.create

class RetrofitClient(retrofit: Retrofit): ApiService by retrofit.create()