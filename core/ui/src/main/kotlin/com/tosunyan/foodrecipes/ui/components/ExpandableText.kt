package com.tosunyan.foodrecipes.ui.theme.components

import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import com.inconceptlabs.designsystem.theme.AppTheme
import com.tosunyan.foodrecipes.ui.R
import okio.utf8Size

private const val TagShowLess = "tag.show_less"
private const val TagShowMore = "tag.show_more"

@Composable
fun ExpandableText(
    text: String,
    maxLines: Int = 3,
    expandText: String = stringResource(id = R.string.see_more),
    collapseText: String = stringResource(id = R.string.see_less),
    style: TextStyle = AppTheme.typography.P4,
    color: Color = AppTheme.colorScheme.T8,
    expandStyle: TextStyle = style,
    expandColor: Color = color,
    modifier: Modifier,
) {
    var isExpanded by remember { mutableStateOf(false) }
    var truncated by remember { mutableStateOf(false) }
    var lastVisibleCharacterIndex by remember { mutableIntStateOf(0) }

    val expandSpanStyle = expandStyle.copy(color = expandColor).toSpanStyle()
    val clickableAnnotation = clickableAnnotation(
        style = expandStyle,
        color = expandColor,
        action = {}
    )

    val annotatedString = buildAnnotatedString {
        when {
            truncated && isExpanded -> {
                append("$text\t")

                withLink(LinkAnnotation.Clickable(TagShowLess) { isExpanded = false }) {
                    withStyle(expandSpanStyle) { append(collapseText) }
                }
            }
            truncated -> {
                val ellipsis = stringResource(id = R.string.ellipsis)
                val count = lastVisibleCharacterIndex - expandText.length - 1
                val truncatedText = text.take(count)

                append("$truncatedText ")
                withLink(
                    link = clickableAnnotation(style) { isExpanded = true },
                    block = { append(expandText) }
                )
            }
            else -> {
                append(text)
            }
        }
    }

    BasicText(
        text = annotatedString,
        style = style.copy(color = color),
        maxLines = if (isExpanded) Int.MAX_VALUE else maxLines,
        onTextLayout = { textLayoutResult ->
            if (isExpanded || lastVisibleCharacterIndex != 0) return@BasicText

            lastVisibleCharacterIndex = textLayoutResult.getLineEnd(
                lineIndex = maxLines - 1,
                visibleEnd = true
            )
            val a = lastVisibleCharacterIndex
            truncated = textLayoutResult.hasVisualOverflow
        },
        modifier = modifier
    )
}

private fun clickableAnnotation(
    style: TextStyle,
    color: Color,
    pressedColor: Color,
    action: (LinkAnnotation) -> Unit
): LinkAnnotation.Clickable {
    val spanStyle = style.copy(color = color).toSpanStyle()
    val styles = TextLinkStyles(
        style = spanStyle,
        pressedStyle = spanStyle.copy(color = color)
    )

    return LinkAnnotation.Clickable(
        tag = TagShowMore,
        styles = styles,
        linkInteractionListener = action
    )
}