package com.tosunyan.foodrecipes.ui.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.inconceptlabs.designsystem.components.core.Icon
import com.inconceptlabs.designsystem.components.core.Text
import com.inconceptlabs.designsystem.theme.AppTheme
import com.tosunyan.foodrecipes.model.MealDetailsModel
import com.tosunyan.foodrecipes.ui.R
import com.tosunyan.foodrecipes.ui.theme.Gray100
import com.tosunyan.foodrecipes.ui.theme.shimmerBrush

private const val ItemHeight = 160

@Composable
fun DailySpecialItem(
    item: MealDetailsModel,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    onClick: (MealDetailsModel) -> Unit,
) {
    var isImageLoading by remember { mutableStateOf(true) }
    val itemShape = RoundedCornerShape(8.dp)
    val imageShape = itemShape.copy(
        topEnd = CornerSize(0.dp),
        bottomEnd = CornerSize(0.dp),
    )

    Row(
        modifier = modifier
            .height(ItemHeight.dp)
            .clickable { onClick(item) }
            .background(color = Gray100, shape = itemShape)
    ) {
        AsyncImage(
            model = item.thumbnail,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            onLoading = { isImageLoading = true },
            onSuccess = { isImageLoading = false },
            onError = { isImageLoading = false },
            modifier = Modifier
                .fillMaxWidth(0.45f)
                .fillMaxHeight()
                .clip(shape = imageShape)
                .background(shimmerBrush(isLoading || isImageLoading))
        )

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                text = item.name,
                style = AppTheme.typography.S2,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
            )

            Spacer(modifier = Modifier.height(8.dp))

            listOf(
                item.region to R.drawable.ic_region,
                item.category to R.drawable.ic_category,
            ).forEach { (text, iconId) ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 8.dp),
                ) {
                    Icon(
                        painter = painterResource(iconId),
                        tint = AppTheme.colorScheme.T8,
                    )
                    Text(
                        text = text,
                        style = AppTheme.typography.P4,
                    )
                }
            }
        }
    }
}