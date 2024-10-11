package com.tosunyan.foodrecipes.ui.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.verify.domain.DomainVerificationManager
import android.content.pm.verify.domain.DomainVerificationUserState
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.core.net.toUri
import androidx.core.os.bundleOf
import com.tosunyan.foodrecipes.ui.R

private val isMinAndroid12: Boolean
    get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

fun Context.openLink(uriString: String) {
    Intent()
        .apply {
            action = Intent.ACTION_VIEW
            data = uriString.toUri()
        }
        .let(::startActivityIfFound)
}

fun Context.launchIntentChooser(
    vararg pairs: Pair<String, Any?>,
) {
    Intent()
        .apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION

            val extras = bundleOf(*pairs)
            putExtras(extras)
        }
        .let { Intent.createChooser(it, "") }
        .let(::startActivity)
}

fun Context.launchAppLinksSettings() {
    if (!isMinAndroid12) return

    Intent()
        .apply {
            action = Settings.ACTION_APP_OPEN_BY_DEFAULT_SETTINGS
            data = "package:$packageName".toUri()
        }
        .let(::startActivityIfFound)
}

fun Context.areAppLinksVerified(): Boolean {
    if (!isMinAndroid12) return true

    val manager = getSystemService(DomainVerificationManager::class.java)
    val userState = manager.getDomainVerificationUserState(packageName) ?: return false

    val allLinksAreVerified = userState.hostToStateMap.all {
        it.value == DomainVerificationUserState.DOMAIN_STATE_VERIFIED
    }
    val userHasVerifiedManually = userState.hostToStateMap.any {
        it.value == DomainVerificationUserState.DOMAIN_STATE_SELECTED
    }
    return allLinksAreVerified || userHasVerifiedManually
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