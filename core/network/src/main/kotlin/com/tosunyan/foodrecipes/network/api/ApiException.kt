package com.tosunyan.foodrecipes.network.api

open class ApiException : Throwable {

    constructor(message: String?) : super(message ?: "Unknown network error")

    constructor(
        statusCode: Int,
        apiMessage: String? = null,
        errorBody: String,
    ) : super("$statusCode $apiMessage \n$errorBody")
}

class NullBodyException(
    statusCode: Int,
    message: String? = null,
) : ApiException(
    message = "$statusCode ${message ?: "Null body"}"
)