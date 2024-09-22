package com.tosunyan.foodrecipes.ui.utils

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

fun Context.showToast(
    @StringRes textResId: Int,
    longDuration: Boolean = false,
) {
    val text = getString(textResId)
    showToast(text, longDuration)
}

fun Context.showToast(
    text: CharSequence,
    longDuration: Boolean = false,
) {
    val length = if (longDuration) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
    Toast.makeText(this, text, length).show()
}