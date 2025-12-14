package com.tosunyan.foodrecipes.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList

@Composable
fun rememberBackStack(): BackStack = remember(::BackStack)

@JvmInline
value class BackStack(
    override val value: SnapshotStateList<Any> = mutableStateListOf(),
): State<SnapshotStateList<Any>> {

    val size: Int get() = value.size
    val isEmpty: Boolean get() = value.isEmpty()
    val lastOrNull: Any? get() = value.lastOrNull()

    val rawValue: BackStack
        get() = this

    fun push(item: Any) {
        value.add(item)
    }

    fun pop() {
        if (value.isNotEmpty()) {
            value.removeAt(value.size - 1)
        }
    }
}