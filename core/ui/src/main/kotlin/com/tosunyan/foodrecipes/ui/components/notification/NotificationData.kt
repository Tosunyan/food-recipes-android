package com.tosunyan.foodrecipes.ui.components.notification

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.Modifier
import com.inconceptlabs.designsystem.theme.AppTheme
import com.tosunyan.foodrecipes.ui.theme.ColorProducer
import com.tosunyan.foodrecipes.ui.theme.TextStyleProducer
import kotlin.time.Duration

data class NotificationData(
    @StringRes
    val titleResId: Int,
    val titleStyle: TextStyleProducer = { AppTheme.typography.S4 },

    @StringRes
    val subtitleResId: Int? = null,
    val subtitleStyle: TextStyleProducer = { AppTheme.typography.L3 },

    @DrawableRes
    val startIconResId: Int? = null,
    @DrawableRes
    val endIconResId: Int? = null,

    @StringRes
    val buttonTextResId: Int? = null,
    val contentColor: ColorProducer = { AppTheme.colorScheme.T8 },
    val iconTint: ColorProducer = contentColor,
    val backgroundColor: ColorProducer = { AppTheme.colorScheme.BG2 },
    val contentPadding: PaddingValues = defaultPadding,
    val modifier: Modifier = Modifier,
    val onClick: (() -> Unit)? = null
)