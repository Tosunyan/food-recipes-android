package com.tosunyan.foodrecipes.ui.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.net.toUri
import com.tosunyan.foodrecipes.ui.R

fun Context.openLink(uriString: String) {
    Intent()
        .apply {
            action = Intent.ACTION_VIEW
            data = uriString.toUri()
        }
        .let(::startActivityIfFound)
}

private fun Context.startActivityIfFound(
    intent: Intent,
    options: Bundle? = null,
) {
    try {
        startActivity(intent, options)
    } catch (e: ActivityNotFoundException) {
        showToast(R.string.error_activity_not_found)
    }
}