package com.tosunyan.foodrecipes.ui.deeplink

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.core.util.Consumer
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class DeepLinkProducer(
    private val activity: ComponentActivity,
): DefaultLifecycleObserver {

    private val listener = Consumer<Intent> { intent ->
        intent.toDeepLink()?.let(::emitDeepLink)
    }

    init {
        val openedActivityIntent = activity.intent
        listener.accept(openedActivityIntent)

        activity.addOnNewIntentListener(listener)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        activity.removeOnNewIntentListener(listener)
    }

    private fun emitDeepLink(deepLink: DeepLink) {
        activity.lifecycleScope.launch {
            _deepLinkEvents.send(deepLink)
        }
    }

    companion object {

        private val _deepLinkEvents = Channel<DeepLink>()
        val deepLinkEvents = _deepLinkEvents.receiveAsFlow()
    }
}