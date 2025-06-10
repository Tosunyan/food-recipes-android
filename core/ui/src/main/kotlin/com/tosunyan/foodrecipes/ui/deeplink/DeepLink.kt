package com.tosunyan.foodrecipes.ui.deeplink

import android.content.Intent
import android.net.Uri

@JvmInline
value class DeepLink(private val value: Intent) {

    val data: Uri?
        get() = value.data
}