@file:OptIn(ExperimentalContracts::class)

package com.tosunyan.foodrecipes.network.api

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

sealed class ApiResponse<out T> {

    data class Success<out T>(val data: T) : ApiResponse<T>()

    data class Failure(
        var message: String?,
        var status: Int?,
        var errorCode: String?
    ) : ApiResponse<Nothing>()
}

fun <T> ApiResponse<T>.isSuccess(): Boolean {
    contract {
        returns(true) implies (this@isSuccess is ApiResponse.Success<T>)
        returns(false) implies (this@isSuccess is ApiResponse.Failure)
    }

    return this is ApiResponse.Success
}

fun <T> ApiResponse<T>.onSuccess(action: T.() -> Unit): ApiResponse<T> {
    if (isSuccess()) {
        this.data.action()
    }

    return this
}

fun <T, R> ApiResponse<T>.mapOnSuccess(action: T.() -> R): ApiResponse<R> {
    return when (this) {
        is ApiResponse.Success -> ApiResponse.Success(data.action())
        is ApiResponse.Failure -> this
    }
}