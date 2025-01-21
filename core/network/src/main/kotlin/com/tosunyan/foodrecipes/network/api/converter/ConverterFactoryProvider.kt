package com.tosunyan.foodrecipes.network.api.converter

import retrofit2.Converter

interface ConverterFactoryProvider {

    val value: Converter.Factory
}