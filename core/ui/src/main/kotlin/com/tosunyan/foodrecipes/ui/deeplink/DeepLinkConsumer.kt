package com.tosunyan.foodrecipes.ui.deeplink

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class DeepLinkConsumer(coroutineScope: CoroutineScope) {

    init {
        DeepLinkProducer.deepLinkEvents
            .onEach(::DeepLinkHandler)
            .launchIn(coroutineScope)
    }

    fun deepLinkHandler(deepLink: DeepLink): DeepLinkHandler<*>? {
        return when (deepLink) {
            is MealDeepLink -> MealDeepLinkHandler(deepLink)
            else -> null
        }
    }
}