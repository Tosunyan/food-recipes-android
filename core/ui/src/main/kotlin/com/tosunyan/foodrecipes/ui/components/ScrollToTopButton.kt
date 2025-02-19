package com.tosunyan.foodrecipes.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.inconceptlabs.designsystem.components.buttons.ButtonType
import com.inconceptlabs.designsystem.components.buttons.TextButton
import com.inconceptlabs.designsystem.theme.attributes.KeyColor
import com.inconceptlabs.designsystem.theme.attributes.Size
import com.tosunyan.foodrecipes.ui.R
import com.tosunyan.foodrecipes.ui.utils.animateScrollToTop
import com.tosunyan.foodrecipes.ui.utils.firstVisibleItemIndex
import com.tosunyan.foodrecipes.ui.utils.visibleItemsLastIndex
import com.tosunyan.foodrecipes.ui.utils.visibleItemsSize
import kotlinx.coroutines.launch

@Composable
fun ScrollToTopButton(
    scrollableState: ScrollableState,
    modifier: Modifier = Modifier,
    text: String = stringResource(R.string.top),
    icon: Painter = painterResource(R.drawable.ic_arrow_up),
) {
    val scope = rememberCoroutineScope()

    val initialLastVisibleItemIndex by remember(scrollableState.firstVisibleItemIndex) {
        val value = scrollableState.visibleItemsLastIndex.takeIf {
            scrollableState.firstVisibleItemIndex == 0
        }
        mutableIntStateOf(value ?: -1)
    }

    val isVisible by remember(scrollableState) {
//        derivedStateOf {
        mutableStateOf(with(scrollableState) {
            println("""
                {
                    firstVisibleItemIndex: $firstVisibleItemIndex
                    visibleItemsSize: $initialLastVisibleItemIndex
                    lastScrolledBackward: $lastScrolledBackward
                }
                """.trimIndent())
            firstVisibleItemIndex > initialLastVisibleItemIndex
                && lastScrolledBackward
                && canScrollBackward
        })
//        }
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(initialOffsetY = { it / 2 }) + fadeIn(),
        exit = slideOutVertically(targetOffsetY = { it / 2 }) + fadeOut(),
        modifier = modifier
            .padding(bottom = 16.dp),
    ) {
        TextButton(
            text = text,
            startIcon = icon,
            size = Size.S,
            type = ButtonType.SECONDARY,
            keyColor = KeyColor.SECONDARY,
            onClick = {
                scope.launch { scrollableState.animateScrollToTop() }
            }
        )
    }
}