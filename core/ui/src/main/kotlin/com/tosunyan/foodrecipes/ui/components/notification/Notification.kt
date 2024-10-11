package com.tosunyan.foodrecipes.ui.components.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.inconceptlabs.designsystem.components.buttons.TextButton
import com.inconceptlabs.designsystem.components.core.Icon
import com.inconceptlabs.designsystem.components.core.Text
import com.inconceptlabs.designsystem.theme.AppTheme
import com.inconceptlabs.designsystem.theme.attributes.KeyColor
import com.inconceptlabs.designsystem.theme.attributes.Size
import com.inconceptlabs.designsystem.theme.colors.paletteColors
import com.tosunyan.foodrecipes.ui.R

@Composable
fun Notification(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
    keyColor: KeyColor = KeyColor.SECONDARY,
    startIcon: Painter? = null,
    endIcon: Painter? = null,
    buttonText: String? = null,
    onButtonClick: () -> Unit = { },
    onEndIconClick: () -> Unit = { },
) {
    val paletteColors = keyColor.paletteColors()

    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .background(paletteColors.alpha10)
            .statusBarsPadding()
            .padding(horizontal = 12.dp, vertical = 12.dp)
    ) {
        val (
            titleRef,
            subtitleRef,
            startIconRef,
            endIconRef,
            buttonRef,
        ) = createRefs()

        if (startIcon != null) {
            Icon(
                painter = startIcon,
                tint = keyColor.paletteColors().main,
                modifier = Modifier
                    .constrainAs(startIconRef) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                    }
                    .size(20.dp)
            )
        }

        Text(
            text = title,
            style = AppTheme.typography.P5,
            color = paletteColors.main,
            maxLines = 1,
            modifier = Modifier.constrainAs(titleRef) {
                if (startIcon != null) {
                    start.linkTo(startIconRef.end, margin = 8.dp)
                } else {
                    start.linkTo(parent.start)
                }

                val endAnchor = when {
                    buttonText != null -> buttonRef.start
                    endIcon != null -> endIconRef.start
                    else -> parent.end
                }
                val endMargin = if (endAnchor != parent.end) 12.dp else 0.dp

                end.linkTo(endAnchor, endMargin)
                top.linkTo(parent.top)

                width = Dimension.fillToConstraints
            }
        )

        Text(
            text = subtitle,
            style = AppTheme.typography.P5,
            maxLines = 2,
            modifier = Modifier.constrainAs(subtitleRef) {
                start.linkTo(titleRef.start)
                end.linkTo(titleRef.end)

                if (buttonText != null) {
                    top.linkTo(buttonRef.top)
                    bottom.linkTo(buttonRef.bottom)
                } else {
                    top.linkTo(titleRef.bottom)
                }

                width = Dimension.fillToConstraints
            }
        )

        if (buttonText != null) {
            TextButton(
                text = buttonText,
                size = Size.XXS,
                keyColor = keyColor,
                onClick = onButtonClick,
                modifier = Modifier.constrainAs(buttonRef) {
                    if (endIcon != null) {
                        end.linkTo(endIconRef.start, margin = 12.dp)
                    } else {
                        end.linkTo(parent.end)
                    }

                    top.linkTo(titleRef.bottom)
                    bottom.linkTo(parent.bottom)
                }
            )
        }

        if (endIcon != null) {
            Icon(
                painter = endIcon,
                tint = paletteColors.main,
                modifier = Modifier
                    .constrainAs(endIconRef) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                    .clickable(onClick = onEndIconClick)
                    .size(16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ComponentPreview() {
    AppTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Notification(
                startIcon = painterResource(id = R.drawable.ic_warning_fill),
                title = "Account Blocked",
                subtitle = "Account blocked due to suspicious activity",
                keyColor = KeyColor.ERROR,
            )
            Notification(
                startIcon = painterResource(id = R.drawable.ic_warning_fill),
                endIcon = painterResource(id = R.drawable.ic_back),
                title = "Please verify your account.",
                subtitle = "If you have not received the email",
                buttonText = "Resend",
                keyColor = KeyColor.WARNING,
            )
            Notification(
                startIcon = painterResource(id = R.drawable.ic_warning_fill),
                title = "Premium Account",
                subtitle = "Activate to enjoy all the benefits!",
                buttonText = "Upgrade",
                keyColor = KeyColor.PREMIUM,
            )
            Notification(
                startIcon = painterResource(id = R.drawable.ic_bookmark_fill),
                endIcon = painterResource(id = R.drawable.ic_back),
                title = "Upcoming Maintenance",
                subtitle = "Please be informed that maintenance is at 4:00 AM",
                keyColor = KeyColor.INFO,
            )
        }
    }
}