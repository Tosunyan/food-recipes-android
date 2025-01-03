package com.tosunyan.foodrecipes.common.utils

import android.util.Log
import com.tosunyan.foodrecipes.common.BuildConfig

fun Any.logException(exception: Throwable) {
    logException(
        tag = this::class.simpleName,
        exception = exception
    )
}

fun logInfo(tag: String?, message: String) {
    Log.i(tag, message)
}

fun logException(tag: String?, exception: Throwable) {
    if (BuildConfig.DEBUG) throw exception

    Log.e(
        tag ?: "TAG",
        exception.message ?: "Unknown error!"
    )
}