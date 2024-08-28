package com.tosunyan.foodrecipes.common.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface DispatcherProvider {

    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher

    companion object {

        val default: DispatcherProvider = DefaultDispatcherProvider
    }
}

private object DefaultDispatcherProvider: DispatcherProvider {

    override val main = Dispatchers.Main
    override val io = Dispatchers.IO
    override val default = Dispatchers.Default
}