package com.tosunyan.foodrecipes.ui.theme

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.ViewModel

typealias ColorProducer = @Composable () -> Color
typealias TextStyleProducer = @Composable () -> TextStyle

/**
 * Utility function is useful for obtaining a
 * [Painter] in a non-composable context, such
 * as in a [ViewModel] or a utility class.
 */
fun Context.painterResource(@DrawableRes id: Int): Painter {
    return getDrawable(id)!!
        .toBitmap()
        .asImageBitmap()
        .let(::BitmapPainter)
}