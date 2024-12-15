package com.tosunyan.foodrecipes.ui.components.listitem

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.inconceptlabs.designsystem.components.core.Icon
import com.inconceptlabs.designsystem.components.core.Text
import com.inconceptlabs.designsystem.theme.AppTheme
import com.inconceptlabs.designsystem.theme.attributes.KeyColor
import com.inconceptlabs.designsystem.theme.colors.paletteColors
import com.inconceptlabs.designsystem.theme.indication.NoIndication
import com.tosunyan.foodrecipes.ui.R

@Composable
fun ListItem(
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    startIcon: Painter? = null,
    endIcon: Painter? = null,
    keyColor: KeyColor = KeyColor.PRIMARY,
    interactionSource: MutableInteractionSource = remember(::MutableInteractionSource),
    onClick: () -> Unit = {},
) {
    val paletteColors = keyColor.paletteColors()

    val isPressed by interactionSource.collectIsPressedAsState()

    val contentColor = when {
        isPressed -> paletteColors.main
        else -> AppTheme.colorScheme.T8
    }

    val backgroundColor = when {
        isPressed -> AppTheme.colorScheme.BG3
        else -> Color.Transparent
    }

    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                onClick = onClick,
                interactionSource = interactionSource,
                indication = NoIndication,
            )
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(16.dp)
    ) {
        val (startIconRef, titleRef, subtitleRef, endIconRef) = createRefs()

        if (startIcon != null) {
            Icon(
                painter = startIcon,
                tint = contentColor,
                modifier = Modifier
                    .constrainAs(startIconRef) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
            )
        }

        Text(
            text = title,
            style = AppTheme.typography.P4,
            color = contentColor,
            modifier = Modifier
                .constrainAs(titleRef) {
                    val endAnchor = if (endIcon != null) endIconRef.start else parent.end

                    if (startIcon != null) {
                        start.linkTo(startIconRef.end, margin = 10.dp)
                    } else {
                        start.linkTo(parent.start)
                    }

                    end.linkTo(endAnchor)
                    top.linkTo(parent.top)
                    bottom.linkTo(subtitleRef.top)

                    width = Dimension.fillToConstraints
                }
        )

        if (!subtitle.isNullOrBlank()) {
            Text(
                text = subtitle,
                style = AppTheme.typography.P5,
                color = AppTheme.colorScheme.T6,
                modifier = Modifier
                    .constrainAs(subtitleRef) {
                        start.linkTo(titleRef.start)
                        end.linkTo(titleRef.end)
                        top.linkTo(titleRef.bottom, margin = 2.dp)
                        bottom.linkTo(parent.bottom)

                        width = Dimension.fillToConstraints
                    }
            )
        }

        if (endIcon != null) {
            Icon(
                painter = endIcon,
                tint = contentColor,
                modifier = Modifier
                    .constrainAs(endIconRef) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ComponentPreview() {
    AppTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            ListItem(
                title = "List item title",
            )
            ListItem(
                title = "List item title",
                subtitle = "List item subtitle",
            )
            ListItem(
                title = "List item title",
                subtitle = "List item subtitle",
                startIcon = painterResource(R.drawable.ic_text),
            )
            ListItem(
                title = "Link (App Required)",
                subtitle = "They will access this page via sent link",
                startIcon = painterResource(R.drawable.ic_link),
                endIcon = painterResource(R.drawable.ic_eye_closed),
            )
        }
    }
}