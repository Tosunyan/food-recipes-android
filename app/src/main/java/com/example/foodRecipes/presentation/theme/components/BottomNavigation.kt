package com.example.foodRecipes.presentation.theme.components

import androidx.compose.animation.Animatable
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.layout.positionOnScreen
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.foodRecipes.R
import com.example.foodRecipes.presentation.theme.indication.ScaleIndicationNodeFactory
import com.inconceptlabs.designsystem.components.core.Icon
import com.inconceptlabs.designsystem.components.core.Text
import com.inconceptlabs.designsystem.theme.AppTheme
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

data class BottomNavigationItem(
    val icon: Painter,
    val text: String? = null,
    val isSelected: Boolean = false,
    val onClick: () -> Unit = {},
)

@Composable
fun BottomNavigation(
    items: List<BottomNavigationItem>,
    modifier: Modifier = Modifier,
) {
    if (items.isEmpty()) return

    var showHint by remember {
        mutableStateOf(false)
    }
    val hintPosition = remember {
        mutableStateOf<Int?>(null)
    }
    val itemOffsets by remember {
        val default = Array(items.size) { Offset.Zero }
        mutableStateOf(default)
    }

    LaunchedEffect(hintPosition.value) {
        if (hintPosition.value == null) return@LaunchedEffect

        showHint = true
        delay(1.5.seconds)
        showHint = false
        delay(0.3.seconds)
        hintPosition.value = null
    }

    Box(
        modifier = Modifier.background(Color.Transparent)
    ) {
        NavigationItemHint(
            items = items,
            itemOffsets = itemOffsets,
            showHint = showHint,
            hintPosition = hintPosition.value,
        )

        NavigationItems(
            modifier = modifier,
            items = items,
            itemOffsets = itemOffsets,
            hintPosition = hintPosition
        )
    }
}

@Composable
private fun NavigationItems(
    modifier: Modifier = Modifier,
    items: List<BottomNavigationItem>,
    itemOffsets: Array<Offset>,
    hintPosition: MutableState<Int?>,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .height(56.dp)
            .topBorder(
                color = AppTheme.colorScheme.BG5,
                strokeWidth = 2.dp,
            )
            .background(AppTheme.colorScheme.BG2)
            .padding(horizontal = 20.dp)
    ) {
        items.forEachIndexed { index, item ->
            NavigationItem(
                index = index,
                item = item,
                itemOffsets = itemOffsets,
                hintPosition = hintPosition
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun RowScope.NavigationItem(
    index: Int,
    item: BottomNavigationItem,
    itemOffsets: Array<Offset>,
    hintPosition: MutableState<Int?>,
) {
    val hapticFeedback = LocalHapticFeedback.current

    val iconTint = remember { Animatable(Color.Unspecified) }
    val iconTintDefault = AppTheme.colorScheme.T8
    val iconTintSelected = AppTheme.colorScheme.secondary.dark5

    LaunchedEffect(item.isSelected) {
        iconTint.animateTo(
            targetValue = if (item.isSelected) iconTintSelected else iconTintDefault
        )
    }

    Box(
        modifier = Modifier
            .fillMaxHeight()
            .weight(1f)
            .onGloballyPositioned {
                val x = it.positionOnScreen().x + it.size.width / 2
                itemOffsets[index] = it
                    .positionOnScreen()
                    .copy(x = x)
            }
            .combinedClickable(
                interactionSource = remember(::MutableInteractionSource),
                indication = LocalIndication.current,
                onClick = { item.onClick() },
                onLongClick = {
                    hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                    hintPosition.value = index
                }
            )
    ) {
        Icon(
            painter = item.icon,
            tint = iconTint.value,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
private fun NavigationItemHint(
    items: List<BottomNavigationItem>,
    itemOffsets: Array<Offset>,
    showHint: Boolean,
    hintPosition: Int? = null,
) {
    if (hintPosition == null) return

    var layoutSize by remember {
        mutableStateOf(IntSize.Zero)
    }

    AnimatedVisibility(
        visible = showHint,
        enter = fadeIn(),
        exit = fadeOut(),
        modifier = Modifier
            .offset {
                val pressedItemX = itemOffsets[hintPosition].x.toInt()

                IntOffset(
                    x = pressedItemX - (layoutSize.width / 2),
                    y = -(layoutSize.height + 16.dp.roundToPx()),
                )
            }
            .onSizeChanged { layoutSize = it }
    ) {
        val text = items[hintPosition].text
        if (text.isNullOrBlank()) return@AnimatedVisibility

        Text(
            text = text,
            style = AppTheme.typography.P5,
            color = AppTheme.colorScheme.T1,
            modifier = Modifier
                .zIndex(99f)
                .background(
                    color = Color.Black.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(4.dp)
                )
                .padding(horizontal = 16.dp, vertical = 6.dp)
        )
    }
}

fun Modifier.topBorder(
    color: Color,
    strokeWidth: Dp = 1.dp,
): Modifier {
    return drawWithContent {
        drawContent()
        drawLine(
            color = color,
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f),
            strokeWidth = strokeWidth.toPx()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ComponentPreview() {
    val items = listOf(
        BottomNavigationItem(
            icon = painterResource(id = R.drawable.ic_home),
            isSelected = true,
        ),
        BottomNavigationItem(
            icon = painterResource(id = R.drawable.ic_search),
        ),
        BottomNavigationItem(
            icon = painterResource(id = R.drawable.ic_like),
        )
    )

    AppTheme(indication = ScaleIndicationNodeFactory) {
        Box(modifier = Modifier.fillMaxSize()) {
            BottomNavigation(
                items = items,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
            )
        }
    }
}