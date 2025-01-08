package com.tosunyan.foodrecipes.network.utils

import com.tosunyan.foodrecipes.network.api.ApiException
import com.tosunyan.foodrecipes.network.api.NullBodyException
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response

@Throws(ApiException::class)
fun HttpException.requireResponse(): Response<*> {
    val message = message() ?: "Couldn't resolve response from HttpException"
    return response() ?: throw ApiException("${code()} $message")
}

@Throws(NullBodyException::class)
fun Response<*>.requireErrorBody(): ResponseBody {
    return errorBody() ?: throw NullBodyException(code(), message())
}