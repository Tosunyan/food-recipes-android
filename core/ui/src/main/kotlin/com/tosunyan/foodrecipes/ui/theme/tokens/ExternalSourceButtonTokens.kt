package com.tosunyan.foodrecipes.ui.theme.tokens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.inconceptlabs.designsystem.components.buttons.ButtonType
import com.inconceptlabs.designsystem.components.buttons.token.ButtonTokens
import com.inconceptlabs.designsystem.components.buttons.token.ButtonTokensImpl
import com.inconceptlabs.designsystem.theme.AppTheme
import com.inconceptlabs.designsystem.theme.attributes.Size
import com.inconceptlabs.designsystem.theme.colors.PaletteColors
import com.tosunyan.foodrecipes.ui.theme.Red900

object ExternalSourceButtonTokens : ButtonTokens by ButtonTokensImpl {

    override fun height(size: Size): Dp {
        return Dp.Unspecified
    }

    override fun contentPadding(size: Size): PaddingValues {
        return PaddingValues(horizontal = 16.dp, vertical = 10.dp)
    }

    override fun cornerRadius(size: Size): Dp {
        return 8.dp
    }

    @Composable
    override fun strokeColor(type: ButtonType, palette: PaletteColors): Color {
        return Color.Unspecified
    }

    @Composable
    override fun textStyle(size: Size): TextStyle {
        return AppTheme.typography.B4
    }

    @Composable
    override fun contentColor(type: ButtonType, palette: PaletteColors): Color {
        return Color.White
    }
}

object YouTubeButtonTokens : ButtonTokens by ExternalSourceButtonTokens {

    @Composable
    override fun containerColor(type: ButtonType, palette: PaletteColors): Color {
        return Red900
    }
}

object WebsiteButtonTokens : ButtonTokens by ExternalSourceButtonTokens {

    @Composable
    override fun containerColor(type: ButtonType, palette: PaletteColors): Color {
        return AppTheme.colorScheme.BG10
    }
}