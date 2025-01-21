package com.tosunyan.foodrecipes.network.api.converter

import com.tosunyan.foodrecipes.common.utils.configuredJson
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Converter
import retrofit2.converter.kotlinx.serialization.asConverterFactory

object KotlinXConverterFactory : ConverterFactoryProvider {

    override val value: Converter.Factory
        get() {
            val contentType = "application/json".toMediaType()
            return configuredJson.asConverterFactory(contentType)
        }
}