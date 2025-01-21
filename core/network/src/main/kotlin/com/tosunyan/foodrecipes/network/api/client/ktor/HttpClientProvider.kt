package com.tosunyan.foodrecipes.network.api.client.ktor

import com.tosunyan.foodrecipes.common.utils.configuredJson
import com.tosunyan.foodrecipes.network.api.BaseUrl
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json

class HttpClientProvider(
    baseUrl: BaseUrl,
    engine: HttpClientEngine,
) {

    val value: HttpClient = HttpClient(engine) {
        install(DefaultRequest) {
            url(baseUrl.value)
        }

        install(ContentNegotiation) {
            json(configuredJson)
        }
    }
}