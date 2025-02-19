package com.tosunyan.foodrecipes.ui.utils

import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState

val ScrollableState.firstVisibleItemIndex: Int
    get() = when (this) {
        is LazyListState -> firstVisibleItemIndex
        is LazyGridState -> firstVisibleItemIndex
        else -> 0
    }

val ScrollableState.visibleItemsSize: Int
    get() = when (this) {
        is LazyListState -> layoutInfo.visibleItemsInfo.size
        is LazyGridState -> layoutInfo.visibleItemsInfo.size
        else -> 0
    }

val ScrollableState.visibleItemsLastIndex: Int
    get() = when (this) {
        is LazyListState -> layoutInfo.visibleItemsInfo.lastIndex
        is LazyGridState -> layoutInfo.visibleItemsInfo.lastIndex
        else -> 0
    }

suspend fun ScrollableState.animateScrollToTop() {
    when (this) {
        is LazyListState -> animateScrollToItem(0)
        is LazyGridState -> animateScrollToItem(0)
    }
}