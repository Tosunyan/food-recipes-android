package com.tosunyan.foodrecipes.common.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

fun <T> Flow<T>.collectInScope(
    scope: CoroutineScope,
    collector: FlowCollector<T>
) {
    scope.launch {
        collect(collector)
    }
}