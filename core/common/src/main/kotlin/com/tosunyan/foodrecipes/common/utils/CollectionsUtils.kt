package com.tosunyan.foodrecipes.common.utils

fun <T> List<T>.replace(item: T, replace: (T) -> T): List<T> {
    return map { if (it == item) replace(it) else it }
}