package com.tosunyan.foodrecipes.network.api

import com.tosunyan.foodrecipes.common.coroutines.DispatcherProvider
import com.tosunyan.foodrecipes.network.utils.logApiResponse
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

suspend fun <T> makeApiCall(
    dispatcher: DispatcherProvider = DispatcherProvider.default,
    apiCall: suspend () -> Response<T>,
): ApiResponse<T> = withContext(dispatcher.io) {
        try {
            val response = apiCall.invoke()
            convertResponse(response).also {
                logApiResponse(response.raw().request.url, it)
            }
        } catch (e: UnknownHostException) {
            ApiResponse.Failure("No Internet", -1, "")
        } catch (e: SocketTimeoutException) {
            ApiResponse.Failure("Response time is out", -1, "")
        } catch (e: ConnectException) {
            ApiResponse.Failure("Server is unavailable", -1, "")
        } catch (e: Exception) {
            ApiResponse.Failure(e.message, -1, "")
        }
    }

fun <T> convertResponse(response: Response<T>): ApiResponse<T> {
    val statusCode = response.code()

    if (response.isSuccessful) {
        val body = response.body()
            ?: return ApiResponse.Failure("Null body.", statusCode, "")

        return ApiResponse.Success(body)
    }

    val errorBody = response.errorBody()
        ?: return ApiResponse.Failure(response.message() ?: "Unknown error", statusCode, "")

    return ApiResponse.Failure("Converting failed.", statusCode, errorBody.string())
}