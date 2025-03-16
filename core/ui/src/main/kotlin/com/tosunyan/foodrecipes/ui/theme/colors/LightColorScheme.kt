package com.tosunyan.foodrecipes.ui.theme.colors

import com.inconceptlabs.designsystem.theme.colors.ColorScheme

object LightColorScheme: ColorScheme by ColorScheme.Light {

    override val primary = ColorScheme.Light.secondary
    override val secondary = ColorScheme.Light.primary
}