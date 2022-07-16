package com.example.foodRecipes.data.remote

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

suspend inline fun <T> makeApiCall(crossinline apiCall: suspend () -> Response<T>) = withContext(Dispatchers.IO) {
    try {
        convertResponse(apiCall.invoke())
    } catch (e: UnknownHostException) {
        ApiResponse.Failure("No Internet", -1, "")
    } catch (e: SocketTimeoutException) {
        ApiResponse.Failure("Response time is out", -1, "")
    } catch (e: ConnectException) {
        ApiResponse.Failure("Server is unavailable", -1, "")
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