package com.tosunyan.foodrecipes.ui.components.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintLayoutScope
import com.inconceptlabs.designsystem.theme.AppTheme

internal val defaultPadding: PaddingValues =
    PaddingValues(horizontal = 16.dp, vertical = 12.dp)

@Composable
fun BaseNotification(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = defaultPadding,
    backgroundColor: Color = AppTheme.colorScheme.BG2,
    content: @Composable ConstraintLayoutScope.() -> Unit,
) {
    val shape = RoundedCornerShape(8.dp)

    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .shadow(4.dp, shape)
            .background(
                color = backgroundColor,
                shape = shape,
            )
            .padding(contentPadding),
    ) {
        content()
    }
}