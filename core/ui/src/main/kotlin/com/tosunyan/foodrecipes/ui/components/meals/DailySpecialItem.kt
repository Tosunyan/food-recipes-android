package com.tosunyan.foodrecipes.ui.components.meals

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.inconceptlabs.designsystem.components.core.Icon
import com.inconceptlabs.designsystem.components.core.Text
import com.inconceptlabs.designsystem.theme.AppTheme
import com.tosunyan.foodrecipes.model.MealDetailsModel
import com.tosunyan.foodrecipes.ui.R
import com.tosunyan.foodrecipes.ui.components.Label
import com.tosunyan.foodrecipes.ui.theme.Gray100
import com.tosunyan.foodrecipes.ui.theme.shimmerBrush

@Composable
fun DailySpecialItem(
    item: MealDetailsModel,
    modifier: Modifier = Modifier,
    height: Dp = 140.dp,
    backgroundColor: Color = Gray100,
    isLoading: Boolean = false,
    onClick: (MealDetailsModel) -> Unit,
    onSaveIconClick: (MealDetailsModel) -> Unit,
) {
    var isImageLoading by remember { mutableStateOf(true) }
    val itemShape = RoundedCornerShape(8.dp)
    val imageShape = itemShape.copy(
        topEnd = CornerSize(0.dp),
        bottomEnd = CornerSize(0.dp),
    )

    ConstraintLayout(
        modifier = modifier
            .height(height)
            .clickable { onClick(item) }
            .background(color = backgroundColor, shape = itemShape)
    ) {
        val (thumbnail, title, saveIcon, region, category) = createRefs()

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
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                }
                .aspectRatio(1f)
                .fillMaxHeight()
                .clip(shape = imageShape)
                .background(shimmerBrush(isLoading || isImageLoading))
        )

        val (saveIconResId, saveIconTint) = if (item.isSaved) {
            R.drawable.ic_bookmark_fill to AppTheme.colorScheme.secondary.main
        } else {
            R.drawable.ic_bookmark to AppTheme.colorScheme.T8
        }
        Icon(
            painter = painterResource(id = saveIconResId),
            tint = saveIconTint,
            modifier = Modifier
                .size(36.dp)
                .padding(8.dp)
                .constrainAs(saveIcon) {
                    top.linkTo(parent.top, margin = 4.dp)
                    end.linkTo(parent.end, margin = 8.dp)
                }
                .clickable { onSaveIconClick(item) },
        )

        Text(
            text = item.name,
            style = AppTheme.typography.S2,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2,
            modifier = Modifier
                .constrainAs(title) {
                    top.linkTo(saveIcon.top, margin = 4.dp)
                    start.linkTo(thumbnail.end, margin = 16.dp)
                    end.linkTo(saveIcon.start, margin = 8.dp)

                    width = Dimension.fillToConstraints
                }
        )

        Label(
            text = item.region,
            textColor = AppTheme.colorScheme.T8,
            textStyle = AppTheme.typography.P4,
            icon = painterResource(id = R.drawable.ic_region),
            backgroundColor = Color.Transparent,
            paddingValues = PaddingValues(0.dp),
            modifier = Modifier.constrainAs(region) {
                top.linkTo(title.bottom, margin = 8.dp)
                start.linkTo(title.start)
            }
        )

        Label(
            text = item.category,
            textColor = AppTheme.colorScheme.T8,
            textStyle = AppTheme.typography.P4,
            icon = painterResource(id = R.drawable.ic_category),
            backgroundColor = Color.Transparent,
            paddingValues = PaddingValues(0.dp),
            modifier = Modifier.constrainAs(category) {
                top.linkTo(region.bottom, margin = 8.dp)
                start.linkTo(title.start)
            }
        )
    }
}