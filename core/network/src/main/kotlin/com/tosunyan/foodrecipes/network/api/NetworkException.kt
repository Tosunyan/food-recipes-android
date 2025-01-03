package com.tosunyan.foodrecipes.network.api

open class NetworkException : Throwable {

    constructor(message: String) : super(message)

    constructor(
        statusCode: Int,
        errorBody: String = "",
    ) : super("Network error with status code: $statusCode. Error body: $errorBody")
}

data class NullBodyException(
    val statusCode: Int,
    val errorBody: String = "",
) : NetworkException(
    message = "Null body for status code: $statusCode. Error body: $errorBody"
)