package com.tosunyan.foodrecipes.network.api.client.ktor

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttpConfig
import io.ktor.client.engine.okhttp.OkHttpEngine
import okhttp3.OkHttpClient

class OkHttpEngineProvider(
    httpClient: OkHttpClient
): KtorEngineProvider {

    private val config: OkHttpConfig = OkHttpConfig().apply { preconfigured = httpClient }

    override val value: HttpClientEngine = OkHttpEngine(config)
}