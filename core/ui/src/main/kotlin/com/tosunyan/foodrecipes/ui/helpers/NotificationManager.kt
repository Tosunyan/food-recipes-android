package com.tosunyan.foodrecipes.ui.helpers

import com.tosunyan.foodrecipes.ui.components.notification.NotificationData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.selects.select
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

internal object NotificationManager {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    private val _notificationData = MutableStateFlow<NotificationData?>(null)
    val notificationData = _notificationData.asStateFlow()

    private val _isNotificationVisible = MutableStateFlow(false)
    val isNotificationVisible = _isNotificationVisible.asStateFlow()

    internal val defaultDuration: Duration = 4.seconds
    private val notificationChannel = Channel<Pair<NotificationData, Duration>>(Channel.UNLIMITED)

    init {
        coroutineScope.launch {
            processNotifications()
        }
    }

    fun showNotification(
        notification: NotificationData,
        duration: Duration = defaultDuration,
    ) {
        coroutineScope.launch {
            notificationChannel.send(notification to duration)
        }
    }

    fun hideNotification() {
        coroutineScope.launch {
            _isNotificationVisible.value = false
            delay(0.3.seconds)
            _notificationData.value = null
        }
    }

    private suspend fun processNotifications() {
        notificationChannel.consumeEach { (notification, duration) ->
            _isNotificationVisible.value = true
            _notificationData.value = notification

            coroutineScope.launch {
                delay(duration)
                hideNotification()
            }
        }
    }
}
