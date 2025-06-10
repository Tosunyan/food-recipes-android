package com.tosunyan.foodrecipes.ui.deeplink

import android.content.Intent

val Intent.isDeepLink: Boolean
    get() = action == Intent.ACTION_VIEW
        && hasCategory(Intent.CATEGORY_BROWSABLE)
        && hasCategory(Intent.CATEGORY_DEFAULT)

fun Intent.toDeepLink(): DeepLink? {
    return if (isDeepLink) DeepLink(this) else null
}