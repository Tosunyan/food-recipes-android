package com.example.foodRecipes.presentation.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.foodRecipes.domain.model.IngredientModel
import com.example.foodRecipes.domain.model.MealDetailsModel
import com.example.foodRecipes.presentation.theme.Gray100
import com.example.foodRecipes.presentation.theme.shimmerBrush
import com.inconceptlabs.designsystem.components.core.Text
import com.inconceptlabs.designsystem.theme.AppTheme

private const val ItemHeight = 160
private const val MaxLabelsCount = 6
private const val MaxLabelLength = 15

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DailySpecialItem(
    item: MealDetailsModel,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    onClick: (MealDetailsModel) -> Unit,
) {
    var isImageLoading by remember { mutableStateOf(true) }
    val shape = RoundedCornerShape(8.dp)

    Row(
        modifier = modifier
            .height(ItemHeight.dp)
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
                .fillMaxWidth(0.45f)
                .fillMaxHeight()
                .clip(shape)
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

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 16.dp)
            ) {
                buildSet {
                    add(item.region)
                    add(item.category)
                    addAll(item.ingredients.map(IngredientModel::name))
                }
                    .filter { it.length < MaxLabelLength }
                    .take(MaxLabelsCount)
                    .forEach {
                        Label(text = it)
                    }
            }
        }
    }
}