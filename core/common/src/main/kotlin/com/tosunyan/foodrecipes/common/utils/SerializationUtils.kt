@file:Suppress("unused")

package com.tosunyan.foodrecipes.common.utils

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

val configuredJson = Json {
    isLenient = true
    ignoreUnknownKeys = true
    coerceInputValues = true
}

inline fun <reified T> T.encodeToJson(
    json: Json = configuredJson
): String? {
    return try {
        json.encodeToString(this)
    } catch (exception: Exception) {
        logException("SerializationUtil::encodeToJson", exception)
        null
    }
}

inline fun <reified T> String.decodeToObject(
    json: Json = configuredJson
): T? {
    return try {
        json.decodeFromString(this)
    } catch (exception: Exception) {
        logException("SerializationUtil::decodeToObject", exception)
        null
    }
}