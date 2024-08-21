package com.tosunyan.foodrecipes.common.utils

import android.util.Log
import com.tosunyan.foodrecipes.common.BuildConfig

fun Any.logException(exception: Exception) {
    logException(
        tag = this::class.simpleName,
        exception = exception
    )
}

fun logInfo(tag: String?, message: String) {
    Log.i(tag, message)
}

fun logException(tag: String?, exception: Exception) {
    if (BuildConfig.DEBUG) throw exception

    Log.e(
        tag ?: "TAG",
        exception.message ?: "Unknown error!"
    )
}