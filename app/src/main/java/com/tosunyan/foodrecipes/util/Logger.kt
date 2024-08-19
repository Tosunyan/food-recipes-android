package com.tosunyan.foodrecipes.util

import android.util.Log
import com.tosunyan.foodrecipes.BuildConfig
import com.tosunyan.foodrecipes.datasource.remote.api.ApiResponse
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
    logException(
        tag = this::class.simpleName,
        exception = exception
    )
}

fun logException(
    tag: String? = null,
    exception: Exception,
) {
    if (BuildConfig.DEBUG) throw exception

    val message = exception.message ?: "Unknown error!"
    Log.e(tag ?: "TAG", message)
}