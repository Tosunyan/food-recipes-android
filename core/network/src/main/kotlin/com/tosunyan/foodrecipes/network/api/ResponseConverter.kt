package com.tosunyan.foodrecipes.network.api

import com.tosunyan.foodrecipes.common.coroutines.DispatcherProvider
import com.tosunyan.foodrecipes.common.utils.logException
import com.tosunyan.foodrecipes.network.utils.requireBody
import com.tosunyan.foodrecipes.network.utils.requireErrorBody
import kotlinx.coroutines.withContext
import retrofit2.Response

suspend fun <T> makeApiCall(
    dispatcher: DispatcherProvider = DispatcherProvider.default,
    apiCall: suspend () -> Response<T>,
): Result<T> {
    return withContext(dispatcher.io) {
        runCatching {
            val response = apiCall.invoke()
            response.getBodyOrThrow()
        }
            .onFailure(::logException)
    }
}

@Throws(NullBodyException::class)
private fun <T> Response<T>.getBodyOrThrow(): T {
    val statusCode = code()

    if (isSuccessful) {
        return requireBody(statusCode)
    }

    val errorBody = requireErrorBody(statusCode)
    throw NetworkException(statusCode, errorBody.string())
}