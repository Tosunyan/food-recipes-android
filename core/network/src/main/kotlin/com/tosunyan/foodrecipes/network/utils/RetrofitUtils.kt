package com.tosunyan.foodrecipes.network.utils

import com.tosunyan.foodrecipes.network.api.NullBodyException
import okhttp3.ResponseBody
import retrofit2.Response

@Throws(NullBodyException::class)
fun <T> Response<T>.requireBody(statusCode: Int): T {
    return body() ?: throw NullBodyException(statusCode)
}

@Throws(NullBodyException::class)
fun <T> Response<T>.requireErrorBody(statusCode: Int): ResponseBody {
    return errorBody() ?: throw NullBodyException(statusCode)
}