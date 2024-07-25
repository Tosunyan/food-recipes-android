package com.example.foodRecipes.datasource.remote.api

import com.example.foodRecipes.util.logApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

suspend fun <T> makeApiCall(apiCall: suspend () -> Response<T>) = withContext(Dispatchers.IO) {
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