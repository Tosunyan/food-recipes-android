package com.example.foodRecipes.presentation.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.foodRecipes.domain.model.MealModel
import com.example.foodRecipes.presentation.theme.Gray100
import com.example.foodRecipes.presentation.theme.shimmerBrush
import com.inconceptlabs.designsystem.components.core.Text
import com.inconceptlabs.designsystem.theme.AppTheme

@Composable
fun DailySpecialItem(
    item: MealModel,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    onClick: (MealModel) -> Unit,
) {
    var isImageLoading by remember { mutableStateOf(true) }
    val shape = RoundedCornerShape(8.dp)

    Row(
        modifier = modifier
            .height(160.dp)
            .clickable { onClick(item) }
            .background(color = Gray100, shape = shape)
    ) {
        AsyncImage(
            model = item.thumbnail,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            onLoading = { isImageLoading = true },
            onSuccess = { isImageLoading = false },
            onError = { isImageLoading = false },
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .fillMaxHeight()
                .clip(shape)
                .background(shimmerBrush(isLoading || isImageLoading))
        )

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 16.dp, vertical = 4.dp)
        ) {
            Text(
                text = item.name,
                style = AppTheme.typography.S2,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(top = 8.dp),
            ) {
                Label(tag = item.region)

                Label(tag = item.category)
            }

            Text(
                text = item.ingredients.joinToString(
                    separator = "\n",
                    limit = 2,
                    truncated = "",
                    transform = { "#${it.name}" }
                ),
                style = AppTheme.typography.P5,
                modifier = Modifier.padding(top = 8.dp),
            )
        }
    }
}

@Composable
private fun Label(
    tag: String?,
) {
    Text(
        text = tag ?: return,
        style = AppTheme.typography.P5,
        modifier = Modifier
            .background(
                color = Color.LightGray,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(horizontal = 8.dp, vertical = 1.dp)
    )
}