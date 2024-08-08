package com.example.foodRecipes.util

import android.os.Handler
import android.os.Looper
import android.view.Choreographer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingCommand
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.dropWhile
import kotlinx.coroutines.flow.transformLatest
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

@OptIn(ExperimentalCoroutinesApi::class)
data object WhileSubscribedOrRetained : SharingStarted {

    private val handler = Handler(Looper.getMainLooper())

    private val choreographer: Choreographer
        get() = Choreographer.getInstance()

    override fun command(subscriptionCount: StateFlow<Int>): Flow<SharingCommand> {
        return subscriptionCount
            .transformLatest { count ->
                if (count > 0) {
                    emit(SharingCommand.START)
                } else {
                    awaitChoreographerFramePostFrontOfQueue()
                    emit(SharingCommand.STOP)
                }
            }
            .dropWhile { it != SharingCommand.START }
            .distinctUntilChanged()
    }

    private suspend fun awaitChoreographerFramePostFrontOfQueue() {
        suspendCancellableCoroutine { continuation ->
            val frameCallback = Choreographer.FrameCallback {
                handler.postAtFrontOfQueue {
                    handler.post {
                        if (!continuation.isCompleted) {
                            continuation.resume(Unit)
                        }
                    }
                }
            }

            choreographer.postFrameCallback(frameCallback)

            continuation.invokeOnCancellation {
                choreographer.removeFrameCallback(frameCallback)
            }
        }
    }
}