package com.example.foodRecipes.datasource.remote.api

sealed class ApiResponse<out T> {

    data class Success<out T>(val data: T) : ApiResponse<T>()

    data class Failure(
        var message: String?,
        var status: Int?,
        var errorCode: String?
    ) : ApiResponse<Nothing>()
}

fun <T, R> ApiResponse<T>.mapOnSuccess(action: T.() -> R): ApiResponse<R> {
    return when (this) {
        is ApiResponse.Success -> ApiResponse.Success(data.action())
        is ApiResponse.Failure -> this
    }
}