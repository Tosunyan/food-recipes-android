package com.example.foodRecipes.presentation.theme.components.listitem

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.foodRecipes.domain.model.CategoryModel
import com.example.foodRecipes.presentation.theme.Gray100
import com.inconceptlabs.designsystem.components.core.Text
import com.inconceptlabs.designsystem.theme.AppTheme

@Composable
fun CategoryItem(
    category: CategoryModel,
    onClick: (CategoryModel) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clickable { onClick(category) }
            .background(
                color = Gray100,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(8.dp)
    ) {
        AsyncImage(
            model = category.thumbnail,
            contentScale = ContentScale.Crop,
            contentDescription = category.name,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(4.dp))
        )

        Text(
            text = category.name,
            style = AppTheme.typography.S4,
            modifier = Modifier
        )
    }
}