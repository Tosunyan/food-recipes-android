package com.example.foodRecipes.presentation.theme

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.ViewModel

/**
 * Utility function is useful for obtaining a
 * [Painter] in a non-composable context, such
 * as in a [ViewModel] or a utility class.
 */
fun Context.painterResource(@DrawableRes id: Int): Painter {
    return AppCompatResources.getDrawable(this, id)!!
        .toBitmap()
        .asImageBitmap()
        .let(::BitmapPainter)
}