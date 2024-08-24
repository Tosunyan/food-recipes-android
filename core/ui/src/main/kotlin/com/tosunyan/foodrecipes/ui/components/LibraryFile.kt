package com.tosunyan.foodrecipes.ui.theme.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.DrawModifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import kotlin.math.max

@JvmInline
value class ReadMoreTextOverflow private constructor(internal val value: Int) {

    override fun toString(): String {
        return when (this) {
            Clip -> "Clip"
            Ellipsis -> "Ellipsis"
            else -> "Invalid"
        }
    }

    companion object {
        val Clip: ReadMoreTextOverflow = ReadMoreTextOverflow(1)
        val Ellipsis: ReadMoreTextOverflow = ReadMoreTextOverflow(2)
    }
}

@JvmInline
value class ToggleArea private constructor(internal val value: Int) {

    override fun toString(): String {
        return when (this) {
            All -> "All"
            More -> "More"
            else -> "Invalid"
        }
    }

    companion object {
        @Stable
        val All: ToggleArea = ToggleArea(1)

        @Stable
        val More: ToggleArea = ToggleArea(2)
    }
}

@Composable
fun BasicReadMoreText(
    text: String,
    expanded: Boolean,
    modifier: Modifier = Modifier,
    onExpandedChange: ((Boolean) -> Unit)? = null,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    style: TextStyle = TextStyle.Default,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    softWrap: Boolean = true,
    readMoreText: String = "",
    readMoreMaxLines: Int = 2,
    readMoreOverflow: ReadMoreTextOverflow = ReadMoreTextOverflow.Ellipsis,
    readMoreStyle: SpanStyle = style.toSpanStyle(),
    readLessText: String = "",
    readLessStyle: SpanStyle = readMoreStyle,
    toggleArea: ToggleArea = ToggleArea.All,
) {
    CoreReadMoreText(
        text = AnnotatedString(text),
        expanded = expanded,
        modifier = modifier,
        onExpandedChange = onExpandedChange,
        contentPadding = contentPadding,
        style = style,
        onTextLayout = onTextLayout,
        softWrap = softWrap,
        readMoreText = readMoreText,
        readMoreMaxLines = readMoreMaxLines,
        readMoreOverflow = readMoreOverflow,
        readMoreStyle = readMoreStyle,
        readLessText = readLessText,
        readLessStyle = readLessStyle,
        toggleArea = toggleArea,
    )
}

@Composable
fun BasicReadMoreText(
    text: AnnotatedString,
    expanded: Boolean,
    modifier: Modifier = Modifier,
    onExpandedChange: ((Boolean) -> Unit)? = null,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    style: TextStyle = TextStyle.Default,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    softWrap: Boolean = true,
    readMoreText: String = "",
    readMoreMaxLines: Int = 2,
    readMoreOverflow: ReadMoreTextOverflow = ReadMoreTextOverflow.Ellipsis,
    readMoreStyle: SpanStyle = style.toSpanStyle(),
    readLessText: String = "",
    readLessStyle: SpanStyle = readMoreStyle,
    toggleArea: ToggleArea = ToggleArea.All,
) {
    CoreReadMoreText(
        text = text,
        expanded = expanded,
        modifier = modifier,
        onExpandedChange = onExpandedChange,
        contentPadding = contentPadding,
        style = style,
        onTextLayout = onTextLayout,
        softWrap = softWrap,
        readMoreText = readMoreText,
        readMoreMaxLines = readMoreMaxLines,
        readMoreOverflow = readMoreOverflow,
        readMoreStyle = readMoreStyle,
        readLessText = readLessText,
        readLessStyle = readLessStyle,
        toggleArea = toggleArea,
    )
}

private const val ReadMoreTag = "read_more"
private const val ReadLessTag = "read_less"

@Composable
private fun CoreReadMoreText(
    text: AnnotatedString,
    expanded: Boolean,
    modifier: Modifier = Modifier,
    onExpandedChange: ((Boolean) -> Unit)? = null,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    style: TextStyle = TextStyle.Default,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    softWrap: Boolean = true,
    readMoreText: String = "",
    readMoreMaxLines: Int = 2,
    readMoreOverflow: ReadMoreTextOverflow = ReadMoreTextOverflow.Ellipsis,
    readMoreStyle: SpanStyle = style.toSpanStyle(),
    readLessText: String = "",
    readLessStyle: SpanStyle = readMoreStyle,
    toggleArea: ToggleArea = ToggleArea.All,
) {
    require(readMoreMaxLines > 0) { "readMoreMaxLines should be greater than 0" }

    val overflowText: String = remember(readMoreOverflow) {
        buildString {
            when (readMoreOverflow) {
                ReadMoreTextOverflow.Clip -> {
                }
                ReadMoreTextOverflow.Ellipsis -> {
                    append(Typography.ellipsis)
                }
            }
            if (readMoreText.isNotEmpty()) {
                append(Typography.nbsp)
            }
        }
    }
    val readMoreTextWithStyle: AnnotatedString = remember(readMoreText, readMoreStyle) {
        buildAnnotatedString {
            if (readMoreText.isNotEmpty()) {
                withStyle(readMoreStyle) {
                    append(readMoreText.replace(' ', Typography.nbsp))
                }
            }
        }
    }
    val readLessTextWithStyle: AnnotatedString = remember(readLessText, readLessStyle) {
        buildAnnotatedString {
            if (readLessText.isNotEmpty()) {
                withStyle(readLessStyle) {
                    append(readLessText)
                }
            }
        }
    }

    val state = remember(text, readMoreMaxLines) {
        ReadMoreState(
            originalText = text,
            readMoreMaxLines = readMoreMaxLines,
        )
    }
    val currentText = buildAnnotatedString {
        if (expanded) {
            append(text)
            if (readLessTextWithStyle.isNotEmpty()) {
                append(' ')
                withLink(
                    LinkAnnotation.Clickable(tag = ReadLessTag) {
                        onExpandedChange?.invoke(false)
                    },
                ) {
                    append(readLessTextWithStyle)
                }
            }
        } else {
            val collapsedText = state.collapsedText
            if (collapsedText.isNotEmpty()) {
                append(collapsedText)
                append(overflowText)

                withLink(
                    LinkAnnotation.Clickable(tag = ReadMoreTag) {
                        onExpandedChange?.invoke(true)
                    },
                ) {
                    append(readMoreTextWithStyle)
                }
            } else {
                append(text)
            }
        }
    }
    val toggleableModifier = if (onExpandedChange != null && toggleArea == ToggleArea.All) {
        Modifier.clickable(
            enabled = state.isCollapsible,
            onClick = { onExpandedChange(!expanded) },
        )
    } else {
        Modifier
    }
    Box(
        modifier = modifier
            .then(toggleableModifier)
            .padding(contentPadding),
    ) {
        if (toggleArea == ToggleArea.More) {
            BasicText(
                text = currentText,
                modifier = Modifier,
                style = style,
                onTextLayout = {
                    state.onTextLayout(it)
                    onTextLayout(it)
                },
                overflow = TextOverflow.Ellipsis,
                softWrap = softWrap,
                maxLines = if (expanded) Int.MAX_VALUE else readMoreMaxLines,
            )
        } else {
            BasicText(
                text = currentText,
                modifier = Modifier,
                style = style,
                onTextLayout = {
                    state.onTextLayout(it)
                    onTextLayout(it)
                },
                overflow = TextOverflow.Ellipsis,
                softWrap = softWrap,
                maxLines = if (expanded) Int.MAX_VALUE else readMoreMaxLines,
            )
        }
        if (expanded.not()) {
            BasicText(
                text = overflowText,
                onTextLayout = { state.onOverflowTextLayout(it) },
                modifier = Modifier.notDraw(),
                style = style,
            )
            BasicText(
                text = readMoreTextWithStyle,
                onTextLayout = { state.onReadMoreTextLayout(it) },
                modifier = Modifier.notDraw(),
                style = style.merge(readMoreStyle),
            )
        }
    }
}

private fun Modifier.notDraw(): Modifier {
    return then(NotDrawModifier)
}

private object NotDrawModifier : DrawModifier {

    override fun ContentDrawScope.draw() {
        // not draws content.
    }
}

@Stable
private class ReadMoreState(
    private val originalText: AnnotatedString,
    private val readMoreMaxLines: Int,
) {
    private var textLayout: TextLayoutResult? = null
    private var overflowTextLayout: TextLayoutResult? = null
    private var readMoreTextLayout: TextLayoutResult? = null

    private var _collapsedText: AnnotatedString by mutableStateOf(AnnotatedString(""))

    var collapsedText: AnnotatedString
        get() = _collapsedText
        internal set(value) {
            if (value != _collapsedText) {
                _collapsedText = value
            }
        }

    val isCollapsible: Boolean
        get() = collapsedText.isNotEmpty()

    fun onTextLayout(result: TextLayoutResult) {
        val lastLineIndex = readMoreMaxLines - 1
        val previous = textLayout
        val old = previous != null &&
                previous.lineCount >= readMoreMaxLines &&
                previous.isLineEllipsized(lastLineIndex)
        val new = result.lineCount >= readMoreMaxLines &&
                result.isLineEllipsized(lastLineIndex)
        val changed = previous != result && old != new
        if (changed) {
            textLayout = result
            updateCollapsedText()
        }
    }

    fun onOverflowTextLayout(result: TextLayoutResult) {
        val changed = overflowTextLayout?.size?.width != result.size.width
        if (changed) {
            overflowTextLayout = result
            updateCollapsedText()
        }
    }

    fun onReadMoreTextLayout(result: TextLayoutResult) {
        val changed = readMoreTextLayout?.size?.width != result.size.width
        if (changed) {
            readMoreTextLayout = result
            updateCollapsedText()
        }
    }

    private fun updateCollapsedText() {
        val lastLineIndex = readMoreMaxLines - 1
        val textLayout = textLayout
        val overflowTextLayout = overflowTextLayout
        val readMoreTextLayout = readMoreTextLayout
        if (textLayout != null &&
            overflowTextLayout != null &&
            readMoreTextLayout != null &&
            textLayout.lineCount >= readMoreMaxLines &&
            textLayout.isLineEllipsized(lastLineIndex)
        ) {
            val countUntilMaxLine = textLayout.getLineEnd(readMoreMaxLines - 1, visibleEnd = true)
            val countUntilMaxLineExceptNewline: Int =
                if (originalText.getOrNull(countUntilMaxLine) == '\n') {
                    // Workaround:
                    // If the last character of the sentence is a newline char('\n'),
                    // calculates excluding the last newline char('\n').
                    countUntilMaxLine - 1
                } else {
                    countUntilMaxLine
                }
            val readMoreWidth = overflowTextLayout.size.width + readMoreTextLayout.size.width
            val maximumWidth = max(0, textLayout.layoutInput.constraints.maxWidth - readMoreWidth)
            var replacedEndIndex = countUntilMaxLineExceptNewline + 1
            var currentTextBounds: Rect
            do {
                replacedEndIndex -= 1
                currentTextBounds = textLayout.getCursorRect(replacedEndIndex)
            } while (currentTextBounds.left > maximumWidth)
            collapsedText = originalText.subSequence(startIndex = 0, endIndex = replacedEndIndex)
        }
    }

    override fun toString(): String {
        return "ReadMoreState(" +
                "originalText=$originalText, " +
                "readMoreMaxLines=$readMoreMaxLines, " +
                "collapsedText=$collapsedText" +
                ")"
    }
}