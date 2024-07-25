package com.example.foodRecipes.util

import android.util.Log
import com.example.foodRecipes.BuildConfig
import com.example.foodRecipes.datasource.remote.api.ApiResponse
import okhttp3.HttpUrl
import okhttp3.Request
import java.lang.Exception

fun logApiRequest(request: Request) {
    val tag = "ApiRequest"
    val message = request.url.toString()

    Log.i(tag, message)
}

fun logApiResponse(url: HttpUrl, response: ApiResponse<*>) {
    Log.i(ApiResponse::class.simpleName, "{\n\turl=${url},\n\tbody=$response\n}")
}

fun Any.logException(exception: Exception) {
    if (BuildConfig.DEBUG) throw exception

    val tag = this::class.simpleName ?: "TAG"
    val message = exception.message ?: "Unknown error!"
    Log.e(tag, message)
}