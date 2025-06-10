package com.tosunyan.foodrecipes.ui.deeplink

import android.net.Uri

interface DeepLinkHandler<Data : Any> {

    val owner: DeepLinkOwner
    val validator: DeepLinkValidator<Data>

    fun onDeepLinkAction(deepLinkData: Data)
}

fun interface DeepLinkOwner {

    fun isDeepLinkOwner(intentData: Uri?): Boolean
}

fun interface DeepLinkValidator<Data> {

    fun isValidDeepLink(intentData: Uri): Boolean
}