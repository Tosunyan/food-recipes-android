package com.example.foodRecipes.presentation.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.inconceptlabs.designsystem.components.core.Text
import com.inconceptlabs.designsystem.theme.AppTheme

@Composable
fun Label(
    text: String,
    textColor: Color = AppTheme.colorScheme.T2,
    textStyle: TextStyle = AppTheme.typography.P5,
    backgroundColor: Color = AppTheme.colorScheme.BG8,
    paddingValues: PaddingValues = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
    onClick: (() -> Unit)? = null,
) {
    Text(
        text = text,
        style = textStyle,
        color = textColor,
        maxLines = 1,
        modifier = Modifier
            .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier)
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(paddingValues)
    )
}