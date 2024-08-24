package com.tosunyan.foodrecipes.ui.components.meals

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.inconceptlabs.designsystem.components.core.Icon
import com.inconceptlabs.designsystem.components.core.Text
import com.inconceptlabs.designsystem.theme.AppTheme
import com.inconceptlabs.designsystem.theme.LocalContentColor
import com.tosunyan.foodrecipes.model.MealModel
import com.tosunyan.foodrecipes.ui.R
import com.tosunyan.foodrecipes.ui.theme.shimmerBrush

@Composable
fun MealItem(
    item: MealModel,
    isLoading: Boolean,
    onClick: (MealModel) -> Unit,
    onSaveIconClick: (MealModel) -> Unit,
) {
    var isImageLoading by remember { mutableStateOf(true) }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onClick(item) })
    ) {
        val (thumbnail, title, saveIcon) = createRefs()

        AsyncImage(
            model = item.thumbnail,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            onLoading = { isImageLoading = true },
            onSuccess = { isImageLoading = false },
            onError = { isImageLoading = false },
            modifier = Modifier
                .constrainAs(thumbnail) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
                .height(180.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(shimmerBrush(isLoading || isImageLoading))
        )

        CompositionLocalProvider(
            LocalContentColor provides Color.White
        ) {
            val saveIconResId = if (item.isSaved) {
                R.drawable.ic_bookmark_fill
            } else {
                R.drawable.ic_bookmark
            }
            Icon(
                painter = painterResource(id = saveIconResId),
                modifier = Modifier
                    .constrainAs(saveIcon) {
                        top.linkTo(thumbnail.top)
                        end.linkTo(thumbnail.end)
                    }
                    .padding(6.dp)
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(Color.Black.copy(alpha = 0.5f))
                    .padding(8.dp)
                    .clickable { onSaveIconClick(item) }
            )
        }

        Text(
            text = item.name,
            style = AppTheme.typography.S3,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2,
            modifier = Modifier
                .constrainAs(title) {
                    top.linkTo(thumbnail.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
                .padding(top = 8.dp)
                .background(shimmerBrush(isLoading))
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ComponentPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        MealItem(
            item = MealModel(
                id = "",
                name = "English Breakfast",
                thumbnail = "https://commons.wikimedia.org/wiki/File:Full_English_breakfast.jpg#/media/File:Full_English_breakfast.jpg"
            ),
            isLoading = false,
            onClick = {},
            onSaveIconClick = {}
        )
    }
}