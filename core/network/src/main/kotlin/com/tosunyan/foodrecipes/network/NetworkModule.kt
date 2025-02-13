package com.tosunyan.foodrecipes.network

import com.tosunyan.foodrecipes.network.api.ApiService
import com.tosunyan.foodrecipes.network.api.BaseUrl
import com.tosunyan.foodrecipes.network.api.OkHttpClientProvider
import com.tosunyan.foodrecipes.network.api.client.ktor.HttpClientProvider
import com.tosunyan.foodrecipes.network.api.client.ktor.KtorClient
import com.tosunyan.foodrecipes.network.api.client.ktor.KtorEngineProvider
import com.tosunyan.foodrecipes.network.api.client.ktor.OkHttpEngineProvider
import com.tosunyan.foodrecipes.network.api.client.retrofit.RetrofitClient
import com.tosunyan.foodrecipes.network.api.client.retrofit.RetrofitProvider
import com.tosunyan.foodrecipes.network.api.converter.ConverterFactoryProvider
import com.tosunyan.foodrecipes.network.api.converter.KotlinXConverterFactory
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import okhttp3.OkHttpClient
import org.koin.core.module.dsl.new
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import kotlin.random.Random

val NetworkModule = module {

    single<BaseUrl> { BaseUrl("${BuildConfig.MEAL_API_URL}/${BuildConfig.MEAL_API_KEY}/") }
    single<ConverterFactoryProvider> { KotlinXConverterFactory }
    single<Converter.Factory> { get<ConverterFactoryProvider>().value }
    single<OkHttpClient> { new(::OkHttpClientProvider).value }
    single<Retrofit> { new(::RetrofitProvider).value }

    single<KtorEngineProvider> { new(::OkHttpEngineProvider) }
    single<HttpClientEngine> { get<KtorEngineProvider>().value }
    single<HttpClient> { new(::HttpClientProvider).value }

    singleOf(::RetrofitClient)
    singleOf(::KtorClient)

    /**
     * This factory will randomly choose between Retrofit and Ktor
     * `Random` is used at the moment, but will be replaced
     * with remote config or other mechanism in the future
     */
    factory<ApiService> {
        if (Random.nextBoolean()) {
            get<RetrofitClient>()
        } else {
            get<KtorClient>()
        }
    }
}