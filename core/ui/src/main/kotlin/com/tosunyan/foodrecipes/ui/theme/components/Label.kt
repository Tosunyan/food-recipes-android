package com.tosunyan.foodrecipes.ui.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import com.inconceptlabs.designsystem.components.core.Icon
import com.inconceptlabs.designsystem.components.core.Text
import com.inconceptlabs.designsystem.theme.AppTheme

data class LabelData(
    val text: String = "",
    val textColor: Color = Color.Unspecified,
    val textStyle: TextStyle = TextStyle.Default,
    val icon: Painter? = null,
    val iconTint: Color = Color.Unspecified,
    val backgroundColor: Color = Color.Unspecified,
    val paddingValues: PaddingValues = PaddingValues(),
    val onClick: (() -> Unit)? = null,
) {


}

@Composable
fun Label(labelData: LabelData) {
    with(labelData) {
        Label(
            text = text,
            textColor = textColor,
            textStyle = textStyle,
            icon = icon,
            iconTint = iconTint,
            backgroundColor = backgroundColor,
            paddingValues = paddingValues,
            onClick = onClick,
        )
    }
}

@Composable
fun Label(
    text: String,
    textColor: Color = AppTheme.colorScheme.T2,
    textStyle: TextStyle = AppTheme.typography.P5,
    icon: Painter? = null,
    iconTint: Color = textColor,
    backgroundColor: Color = AppTheme.colorScheme.BG8,
    paddingValues: PaddingValues = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
    onClick: (() -> Unit)? = null,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier)
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(paddingValues)
    ) {
        icon?.let {
            val density = Density(LocalContext.current)
            val iconSize = with(density) { textStyle.lineHeight.toDp() }

            Icon(
                painter = it,
                tint = iconTint,
                modifier = Modifier.size(iconSize)
            )
        }

        Text(
            text = text,
            style = textStyle,
            color = textColor,
            maxLines = 1,
        )
    }
}