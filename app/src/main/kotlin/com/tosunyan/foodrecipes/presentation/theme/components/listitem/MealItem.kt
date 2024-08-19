package com.tosunyan.foodrecipes.presentation.theme.components.listitem

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.tosunyan.foodrecipes.domain.model.MealModel
import com.tosunyan.foodrecipes.presentation.theme.shimmerBrush
import com.inconceptlabs.designsystem.components.core.Text
import com.inconceptlabs.designsystem.theme.AppTheme

@Composable
fun MealItem(
    item: MealModel,
    isLoading: Boolean,
    onClick: (MealModel) -> Unit,
) {
    var isImageLoading by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .clickable(onClick = { onClick(item) })
    ) {
        AsyncImage(
            model = item.thumbnail,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            onLoading = { isImageLoading = true },
            onSuccess = { isImageLoading = false },
            onError = { isImageLoading = false },
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(shimmerBrush(isLoading || isImageLoading))
        )

        Text(
            text = item.name,
            style = AppTheme.typography.S3,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2,
            modifier = Modifier
                .padding(top = 8.dp)
                .defaultMinSize(minWidth = if (isLoading) 200.dp else 0.dp)
                .background(shimmerBrush(isLoading))
        )
    }
}