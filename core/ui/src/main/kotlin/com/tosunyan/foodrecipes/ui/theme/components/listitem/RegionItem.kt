package com.tosunyan.foodrecipes.ui.theme.components.listitem

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.inconceptlabs.designsystem.components.core.Text
import com.inconceptlabs.designsystem.theme.AppTheme
import com.tosunyan.foodrecipes.model.RegionModel
import com.tosunyan.foodrecipes.ui.theme.Gray100

@Composable
fun RegionItem(
    region: RegionModel,
    onClick: (RegionModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    Text(
        text = region.name,
        style = AppTheme.typography.B4,
        textAlign = TextAlign.Center,
        modifier = modifier
            .clickable { onClick(region) }
            .background(color = Gray100, shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 16.dp, vertical = 12.dp)
    )
}