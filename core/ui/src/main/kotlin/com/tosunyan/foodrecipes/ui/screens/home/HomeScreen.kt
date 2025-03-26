package com.tosunyan.foodrecipes.ui.screens.home

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.inconceptlabs.designsystem.components.core.Text
import com.inconceptlabs.designsystem.theme.AppTheme
import com.tosunyan.foodrecipes.model.CategoryModel
import com.tosunyan.foodrecipes.model.MealDetailsModel
import com.tosunyan.foodrecipes.model.RegionModel
import com.tosunyan.foodrecipes.ui.R
import com.tosunyan.foodrecipes.ui.components.listitem.CategoryItem
import com.tosunyan.foodrecipes.ui.components.listitem.RegionItem
import com.tosunyan.foodrecipes.ui.components.meals.DailySpecialItem
import com.tosunyan.foodrecipes.ui.screens.meals.MealsScreen
import com.tosunyan.foodrecipes.ui.screens.mealdetails.MealDetailsScreen
import com.tosunyan.foodrecipes.ui.theme.FoodRecipesTheme
import org.koin.compose.viewmodel.koinViewModel

class HomeScreen : Tab {

    override val options: TabOptions
        @Composable
        get() = TabOptions(
            index = 0u,
            title = stringResource(id = R.string.navigation_item_home),
            icon = painterResource(id = R.drawable.ic_home),
        )

    @Composable
    override fun Content() {
        val viewModel: HomeViewModel = koinViewModel()
        val navigator = LocalNavigator.current?.parent ?: return

        val errorMessage by viewModel.errorMessage.collectAsState()
        errorMessage?.let {
            Toast.makeText(LocalContext.current, it, Toast.LENGTH_SHORT).show()
        }

        Content(
            dailySpecial = viewModel.randomMeal.collectAsState().value,
            categories = viewModel.categories.collectAsState().value,
            regions = viewModel.regions.collectAsState().value,
            onDailySpecialClick = {
                val screen = MealDetailsScreen(mealDetailsModel = it)
                navigator.push(screen)
            },
            onSaveIconClick = viewModel::onSaveIconClick,
            onCategoryItemClick = {
                val screen = MealsScreen(category = it)
                navigator.push(screen)
            },
            onRegionItemClick = {
                val screen = MealsScreen(region = it)
                navigator.push(screen)
            }
        )
    }

    @Composable
    private fun Content(
        dailySpecial: MealDetailsModel?,
        categories: List<CategoryModel>,
        regions: List<RegionModel>,
        onDailySpecialClick: (MealDetailsModel) -> Unit = {},
        onSaveIconClick: (MealDetailsModel) -> Unit = {},
        onCategoryItemClick: (CategoryModel) -> Unit = {},
        onRegionItemClick: (RegionModel) -> Unit = {},
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(SPAN_COUNT),
            contentPadding = PaddingValues(vertical = 24.dp, horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxSize(),
        ) {
            titleSection()

            dailySpecialSection(
                model = dailySpecial,
                onDailySpecialClick = onDailySpecialClick,
                onSaveIconClick = onSaveIconClick,
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

    @Preview(showBackground = true)
    @Composable
    private fun ContentPreview() {
        FoodRecipesTheme {
            Content(
                dailySpecial = MealDetailsModel(
                    name = "Pepperoni very delicious",
                    category = "Pizza",
                    region = "Italy",
                    isSaved = true,
                ),
                categories = listOf(
                    CategoryModel("Beef", "", ""),
                    CategoryModel("Chicken", "", ""),
                    CategoryModel("Pork", "", ""),
                    CategoryModel("Turkey", "", ""),
                    CategoryModel("Pizza", "", ""),
                    CategoryModel("Pasta", "", ""),
                    CategoryModel("Vegetarian", "", ""),
                    CategoryModel("Desert", "", ""),
                ),
                regions = listOf(
                    RegionModel("Chinese"),
                    RegionModel("American"),
                    RegionModel("Armenian"),
                    RegionModel("Georgian"),
                    RegionModel("Russian"),
                    RegionModel("Indonesian"),
                )
            )
        }
    }

    private fun LazyGridScope.titleSection() {
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
        model: MealDetailsModel? = null,
        onDailySpecialClick: (MealDetailsModel) -> Unit = {},
        onSaveIconClick: (MealDetailsModel) -> Unit = {}
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
                modifier = Modifier.animateItem(),
                onClick = onDailySpecialClick,
                onSaveIconClick = onSaveIconClick,
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

    companion object {

        private const val SPAN_COUNT = 3
    }

    private enum class ContentType {
        ScreenName,
        SectionTitle,
        DailySpecial,
        CategoryItem,
        RegionItem,
    }
}