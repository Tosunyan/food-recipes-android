package com.tosunyan.foodrecipes.ui.components.notification

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.Visibility
import com.inconceptlabs.designsystem.components.buttons.ButtonType
import com.inconceptlabs.designsystem.components.buttons.TextButton
import com.inconceptlabs.designsystem.components.core.Icon
import com.inconceptlabs.designsystem.components.core.Text
import com.inconceptlabs.designsystem.theme.AppTheme
import com.inconceptlabs.designsystem.theme.LocalContentColor
import com.inconceptlabs.designsystem.theme.attributes.KeyColor
import com.inconceptlabs.designsystem.theme.attributes.Size
import com.tosunyan.foodrecipes.ui.R
import com.tosunyan.foodrecipes.ui.helpers.NotificationManager

@Composable
fun Notification(data: NotificationData) {
    data.apply {
        Notification(
            title = stringResource(id = titleResId),
            titleStyle = titleStyle.invoke(),
            subtitle = subtitleResId?.let { stringResource(id = it) },
            subtitleStyle = subtitleStyle.invoke(),
            startIconResId = startIconResId,
            endIconResId = endIconResId,
            buttonText = buttonTextResId?.let { stringResource(id = it) },
            contentColor = contentColor.invoke(),
            iconTint = iconTint.invoke(),
            backgroundColor = backgroundColor.invoke(),
            contentPadding = contentPadding,
            modifier = modifier,
            onClick = onClick,
        )
    }
}

@Composable
fun Notification(
    title: String,
    titleStyle: TextStyle = AppTheme.typography.S4,
    subtitle: String? = null,
    subtitleStyle: TextStyle = AppTheme.typography.L3,
    @DrawableRes startIconResId: Int? = null,
    @DrawableRes endIconResId: Int? = null,
    buttonText: String? = null,
    contentColor: Color = AppTheme.colorScheme.T8,
    iconTint: Color = contentColor,
    backgroundColor: Color = AppTheme.colorScheme.BG2,
    contentPadding: PaddingValues = defaultPadding,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
) {
    BaseNotification(
        modifier = modifier,
        contentPadding = contentPadding,
        backgroundColor = backgroundColor,
    ) {
        val (
            titleRef,
            subtitleRef,
            startIconRef,
            endIconRef,
            actionButtonRef,
        ) = createRefs()

        startIconResId?.let {
            Icon(
                painter = painterResource(id = it),
                tint = iconTint,
                modifier = Modifier
                    .constrainAs(startIconRef) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                    }
                    .size(24.dp)
            )
        }

        Text(
            text = title,
            style = titleStyle,
            color = contentColor,
            modifier = Modifier
                .constrainAs(titleRef) {
                    val bottomAnchor =
                        if (subtitle != null) subtitleRef.top
                        else parent.bottom
                    val startAnchor = when {
                        startIconResId != null -> startIconRef.end
                        else -> parent.start
                    }
                    val endAnchor = when {
                        buttonText != null -> actionButtonRef.start
                        endIconResId != null -> endIconRef.start
                        else -> parent.end
                    }

                    top.linkTo(parent.top)
                    bottom.linkTo(bottomAnchor)
                    start.linkTo(startAnchor, margin = if (startIconResId != null) 16.dp else 0.dp)
                    end.linkTo(endAnchor, margin = 16.dp)

                    width = Dimension.fillToConstraints
                }
        )

        subtitle?.let {
            Text(
                text = it,
                style = subtitleStyle,
                color = contentColor,
                modifier = Modifier
                    .constrainAs(subtitleRef) {
                        top.linkTo(titleRef.bottom)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(titleRef.start)
                        end.linkTo(titleRef.end)

                        width = Dimension.fillToConstraints
                    }
            )
        }

        if (buttonText != null && onClick != null) {
            TextButton(
                text = buttonText,
                type = ButtonType.TEXT,
                keyColor = KeyColor.SECONDARY,
                size = Size.XS,
                hasMinWidth = false,
                modifier = Modifier
                    .constrainAs(actionButtonRef) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(titleRef.end)
                        end.linkTo(parent.end)
                    },
                onClick = {
                    NotificationManager.hideNotification()
                    onClick.invoke()
                }
            )
        }

        if (endIconResId != null && buttonText == null) {
            Icon(
                painter = painterResource(id = endIconResId),
                modifier = Modifier
                    .constrainAs(endIconRef) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                    }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ComponentPreview() {
    AppTheme {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            CompositionLocalProvider(
                LocalContentColor provides AppTheme.colorScheme.T8,
            ) {
                Notification(
                    title = "Item saved successfully!",
                    subtitle = "You can access it in \'Saved Meals\' tab",
                    startIconResId = R.drawable.ic_bookmark_fill,
                    endIconResId = R.drawable.ic_bookmark_fill,
                    buttonText = "Cancel",
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(20.dp)
                )
            }
        }
    }
}