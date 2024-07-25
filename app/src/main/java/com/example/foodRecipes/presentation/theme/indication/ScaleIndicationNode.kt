package com.example.foodRecipes.presentation.theme.indication

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.node.DrawModifierNode
import kotlinx.coroutines.launch

class ScaleIndicationNode(
    private val interactionSource: InteractionSource
) : Modifier.Node(), DrawModifierNode {

    private val animatedScalePercent = Animatable(SCALE_DEFAULT)

    private val animationSpec: AnimationSpec<Float>
        get() = spring(stiffness = Spring.StiffnessHigh)

    override fun onAttach() {
        coroutineScope.launch {
            interactionSource.interactions.collect { interaction ->
                when (interaction) {
                    is PressInteraction.Press -> animateToPressed()
                    is PressInteraction.Release -> animateToDefault()
                    is PressInteraction.Cancel -> animateToDefault()
                }
            }
        }
    }

    override fun ContentDrawScope.draw() {
        scale(
            scale = animatedScalePercent.value,
            pivot = center,
            block = { this@draw.drawContent() }
        )
    }

    private suspend fun animateToPressed() {
        animatedScalePercent.animateTo(
            targetValue = SCALE_PRESSED,
            animationSpec = animationSpec
        )
    }

    private suspend fun animateToDefault() {
        animatedScalePercent.animateTo(
            targetValue = SCALE_DEFAULT,
            animationSpec = animationSpec
        )
    }

    companion object {

        private const val SCALE_DEFAULT = 1f
        private const val SCALE_PRESSED = 0.95f
    }
}