package com.example.foodRecipes.presentation.theme

import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.example.foodRecipes.presentation.theme.indication.ScaleIndicationNodeFactory
import com.inconceptlabs.designsystem.theme.AppTheme

@Suppress("FunctionName")
fun Fragment.ProvideThemedContent(
    content: @Composable () -> Unit
): View {
    return ComposeView(requireContext()).apply {
        setContent {
            AppTheme(indication = ScaleIndicationNodeFactory) {
                content()
            }
        }
    }
}