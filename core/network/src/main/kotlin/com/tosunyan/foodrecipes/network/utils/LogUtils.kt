package com.tosunyan.foodrecipes.network.utils

import com.tosunyan.foodrecipes.common.utils.logInfo
import com.tosunyan.foodrecipes.network.api.ApiResponse
import okhttp3.HttpUrl
import okhttp3.Request

fun logApiRequest(request: Request) {
    val tag = "ApiRequest"
    val message = request.url.toString()

    logInfo(tag, message)
}

fun logApiResponse(url: HttpUrl, response: ApiResponse<*>) {
    logInfo(
        tag = ApiResponse::class.simpleName,
        message = "{\n\turl: ${url},\n\tbody: $response\n}"
    )
}