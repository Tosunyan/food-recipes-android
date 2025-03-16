package com.tosunyan.foodrecipes.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.inconceptlabs.designsystem.components.buttons.IconButton
import com.inconceptlabs.designsystem.components.core.Text
import com.inconceptlabs.designsystem.theme.AppTheme
import com.inconceptlabs.designsystem.theme.attributes.CornerType
import com.inconceptlabs.designsystem.theme.attributes.Size
import com.tosunyan.foodrecipes.ui.R

@Composable
fun Toolbar(
    title: String,
    onBackClick: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .zIndex(1f)
            .shadow(8.dp)
            .background(AppTheme.colorScheme.BG2)
            .statusBarsPadding()
            .padding(horizontal = 16.dp)
            .height(56.dp)
            .fillMaxWidth()
    ) {
        IconButton(
            icon = painterResource(id = R.drawable.ic_back),
            size = Size.S,
            cornerType = CornerType.CIRCULAR,
            onClick = onBackClick
        )

        Text(
            text = title,
            style = AppTheme.typography.S1,
            modifier = Modifier.padding(start = 16.dp),
        )
    }
}