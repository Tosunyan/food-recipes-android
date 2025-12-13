package com.tosunyan.foodrecipes.common.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Suppress("PropertyName")
interface DispatcherProvider {

    val Main: CoroutineDispatcher
    val IO: CoroutineDispatcher
    val Default: CoroutineDispatcher
}

private object DispatcherProviderInstance: DispatcherProvider {

    override val Main = Dispatchers.Main
    override val IO = Dispatchers.IO
    override val Default = Dispatchers.Default
}

fun DispatcherProvider(): DispatcherProvider {
    return DispatcherProviderInstance
}