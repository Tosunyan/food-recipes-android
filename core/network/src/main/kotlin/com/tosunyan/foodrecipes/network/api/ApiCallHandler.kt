package com.tosunyan.foodrecipes.network.api

import com.tosunyan.foodrecipes.common.coroutines.DispatcherProvider
import com.tosunyan.foodrecipes.common.utils.logException
import com.tosunyan.foodrecipes.network.utils.requireErrorBody
import com.tosunyan.foodrecipes.network.utils.requireResponse
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.SocketTimeoutException

suspend fun <T> makeApiCall(
    dispatcher: DispatcherProvider = DispatcherProvider.default,
    apiCall: suspend () -> T,
): Result<T> {
    return withContext(dispatcher.io) {
        runCatching { apiCall() }
            .onFailure { logException("ApiError", it) }
            .recoverCatching(Throwable::toApiException)
    }
}

private fun Throwable.toApiException(): Nothing {
    when (this) {
        is HttpException -> {
            throw this.toApiException()
        }
        is SocketTimeoutException -> {
            throw ApiException("Connection timeout")
        }
        else -> {
            throw ApiException(message)
        }
    }
}

@Throws(ApiException::class, NullBodyException::class)
private fun HttpException.toApiException(): ApiException {
    val response = requireResponse()
    val errorBody = response.requireErrorBody()
    return ApiException(
        statusCode = response.code(),
        apiMessage = response.message(),
        errorBody = errorBody.string()
    )
}