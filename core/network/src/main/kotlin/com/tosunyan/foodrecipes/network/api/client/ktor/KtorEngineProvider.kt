package com.tosunyan.foodrecipes.network.api.client.ktor

import io.ktor.client.engine.HttpClientEngine

interface KtorEngineProvider {

    val value: HttpClientEngine
}