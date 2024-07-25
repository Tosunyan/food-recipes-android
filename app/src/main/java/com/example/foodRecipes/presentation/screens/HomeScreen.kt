package com.example.foodRecipes.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.foodRecipes.R
import com.example.foodRecipes.domain.model.CategoryModel
import com.example.foodRecipes.domain.model.MealModel
import com.example.foodRecipes.domain.model.RegionModel
import com.example.foodRecipes.presentation.theme.components.DailySpecialItem
import com.example.foodRecipes.presentation.theme.components.listitem.CategoryItem
import com.example.foodRecipes.presentation.theme.components.listitem.RegionItem
import com.inconceptlabs.designsystem.components.core.Text
import com.inconceptlabs.designsystem.theme.AppTheme

private const val SPAN_COUNT = 3

@Composable
fun HomeScreen(
    dailySpecial: MealModel?,
    categories: List<CategoryModel>,
    regions: List<RegionModel>,
    onDailySpecialClick: (MealModel) -> Unit = {},
    onCategoryItemClick: (CategoryModel) -> Unit = {},
    onRegionItemClick: (RegionModel) -> Unit = {},
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(SPAN_COUNT),
        contentPadding = PaddingValues(vertical = 24.dp, horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding(),
    ) {
        titleSection()

        dailySpecialSection(
            model = dailySpecial,
            onDailySpecialClick = onDailySpecialClick
        )

        categoriesSection(
            categories = categories,
            onCategoryItemClick = onCategoryItemClick
        )

        regionsSection(
            regions = regions,
            onRegionItemClick = onRegionItemClick
        )
    }
}

private fun LazyGridScope.titleSection(
) {
    item(
        key = "HomeScreen",
        contentType = ContentType.ScreenName,
        span = { GridItemSpan(SPAN_COUNT) }
    ) {
        Text(
            text = stringResource(id = R.string.navigation_item_home),
            style = AppTheme.typography.H4,
            modifier = Modifier
                .animateItem()
                .padding(bottom = 8.dp)
        )
    }
}

private fun LazyGridScope.dailySpecialSection(
    model: MealModel? = null,
    onDailySpecialClick: (MealModel) -> Unit = {},
) {
    model ?: return

    item(
        key = "DailySpecialTitle",
        contentType = ContentType.SectionTitle,
        span = { GridItemSpan(SPAN_COUNT) },
    ) {
        Text(
            text = stringResource(id = R.string.home_daily_special),
            style = AppTheme.typography.S1,
            modifier = Modifier
                .animateItem()
                .padding(bottom = 4.dp)
        )
    }

    item(
        key = "DailySpecialItem",
        contentType = ContentType.DailySpecial,
        span = { GridItemSpan(SPAN_COUNT) }
    ) {
        DailySpecialItem(
            item = model,
            isLoading = false,
            onClick = onDailySpecialClick,
            modifier = Modifier
                .animateItem()
        )
    }
}

private fun LazyGridScope.categoriesSection(
    categories: List<CategoryModel>,
    onCategoryItemClick: (CategoryModel) -> Unit,
) {
    categories.ifEmpty { return }

    item(
        key = "CategoryTitle",
        contentType = ContentType.SectionTitle,
        span = { GridItemSpan(SPAN_COUNT) }
    ) {
        Text(
            text = stringResource(id = R.string.home_categories),
            style = AppTheme.typography.S1,
            modifier = Modifier
                .animateItem()
                .padding(
                    top = 24.dp,
                    bottom = 4.dp
                )
        )
    }

    items(
        key = CategoryModel::name,
        contentType = { ContentType.CategoryItem },
        items = categories
    ) {
        CategoryItem(
            category = it,
            onClick = onCategoryItemClick,
            modifier = Modifier
                .animateItem()
        )
    }
}

private fun LazyGridScope.regionsSection(
    regions: List<RegionModel>,
    onRegionItemClick: (RegionModel) -> Unit,
) {
    regions.ifEmpty { return }

    item(
        key = "RegionsTitle",
        contentType = ContentType.SectionTitle,
        span = { GridItemSpan(SPAN_COUNT) }
    ) {
        Text(
            text = stringResource(id = R.string.home_cuisines),
            style = AppTheme.typography.S1,
            modifier = Modifier
                .animateItem()
                .padding(top = 24.dp, bottom = 4.dp)
        )
    }

    items(
        key = RegionModel::name,
        contentType = { ContentType.RegionItem },
        items = regions
    ) {
        RegionItem(
            region = it,
            onClick = onRegionItemClick,
            modifier = Modifier
                .animateItem()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ScreenPreview() {
    HomeScreen(
        dailySpecial = MealModel("", "Pizza de Italiano"),
        categories = listOf(
            CategoryModel("Beef", "", ""),
            CategoryModel("Chicken", "", ""),
            CategoryModel("Pork", "", ""),
            CategoryModel("Pork", "", ""),
            CategoryModel("Pork", "", ""),
            CategoryModel("Pork", "", ""),
            CategoryModel("Pork", "", ""),
            CategoryModel("Pork", "", ""),
            CategoryModel("Goose", "", ""),
            CategoryModel("Rabbit", "", ""),
            CategoryModel("Cat", "", ""),
        ),
        regions = listOf(
            RegionModel("China"),
            RegionModel("China"),
            RegionModel("China"),
            RegionModel("India"),
            RegionModel("Russia"),
            RegionModel("Indonesia"),
            RegionModel("Indonesia"),
            RegionModel("Indonesia"),
            RegionModel("Indonesia"),
            RegionModel("Indonesia"),
        )
    )
}

private enum class ContentType {
    ScreenName,
    SectionTitle,
    DailySpecial,
    CategoryItem,
    RegionItem,
}