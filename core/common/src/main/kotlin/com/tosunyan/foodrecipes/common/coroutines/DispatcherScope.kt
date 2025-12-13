@file:OptIn(ExperimentalContracts::class)

package com.tosunyan.foodrecipes.common.coroutines

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import kotlin.contracts.ExperimentalContracts

interface DispatcherScope {
    val dispatcherProvider: DispatcherProvider
}

/**
 * Executes a block on the [Dispatchers.IO] and returns a
 * [Result] wrapping either the success value or caught exception.
 */
suspend inline fun <T> DispatcherScope.withIOScope(
    crossinline block: suspend CoroutineScope.() -> T
): Result<T> {
    return withContext(dispatcherProvider.IO) {
        this@withIOScope
            .runCatching { block() }
            .onFailure { it.printStackTraceAndRethrow() }
    }
}

/**
 * Executes a block on the [Dispatchers.IO] and
 * returns the result or null if an exception occurs.
 */
suspend inline fun <T> DispatcherScope.withIOResultOrNull(
    crossinline block: suspend CoroutineScope.() -> T
): T? {
    return withIOScope(block).getOrNull()
}

/**
 * Executes a block on the [Dispatchers.IO] and returns
 * the result or a default value if an exception occurs.
 */
suspend inline fun <T> DispatcherScope.withIOResultOrDefault(
    defaultValue: T,
    crossinline block: suspend CoroutineScope.() -> T
): T {
    return withIOScope(block).getOrDefault(defaultValue)
}

/**
 * Creates a flow that runs on the [Dispatchers.IO].
 */
fun <T> DispatcherScope.ioFlow(block: suspend FlowCollector<T>.() -> Unit): Flow<T> {
    return flow(block)
        .catch { it.printStackTraceAndRethrow() }
        .flowOn(dispatcherProvider.IO)
}

fun Throwable.printStackTraceAndRethrow() {
    printStackTrace()
    if (this is CancellationException) throw this
}