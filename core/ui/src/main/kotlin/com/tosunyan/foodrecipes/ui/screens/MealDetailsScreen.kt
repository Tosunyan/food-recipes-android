package com.tosunyan.foodrecipes.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.AsyncImage
import com.inconceptlabs.designsystem.components.buttons.IconButton
import com.inconceptlabs.designsystem.components.core.Text
import com.inconceptlabs.designsystem.theme.AppTheme
import com.inconceptlabs.designsystem.theme.LocalContentColor
import com.inconceptlabs.designsystem.theme.attributes.CornerType
import com.inconceptlabs.designsystem.theme.attributes.KeyColor
import com.inconceptlabs.designsystem.theme.attributes.Size
import com.tosunyan.foodrecipes.model.CategoryModel
import com.tosunyan.foodrecipes.model.IngredientModel
import com.tosunyan.foodrecipes.model.MealDetailsModel
import com.tosunyan.foodrecipes.model.MealModel
import com.tosunyan.foodrecipes.model.RegionModel
import com.tosunyan.foodrecipes.ui.R
import com.tosunyan.foodrecipes.ui.components.Label
import com.tosunyan.foodrecipes.ui.components.LabelData
import com.tosunyan.foodrecipes.ui.components.TextButton
import com.tosunyan.foodrecipes.ui.components.listitem.IngredientItem
import com.tosunyan.foodrecipes.ui.theme.Red900
import com.tosunyan.foodrecipes.ui.theme.indication.ScaleIndicationNodeFactory
import com.tosunyan.foodrecipes.ui.theme.shimmerBrush
import com.tosunyan.foodrecipes.ui.utils.openLink
import com.tosunyan.foodrecipes.ui.viewmodel.MealDetailsViewModel

class MealDetailsScreen(
    private val mealModel: MealModel? = null,
    private val mealDetailsModel: MealDetailsModel? = null,
) : Screen {

    override val key: ScreenKey
        get() = this::class.simpleName!!

    @Suppress("unused", "PrivatePreviews")
    constructor(): this(null, null)

    @Composable
    override fun Content() {
        val viewModel = viewModel<MealDetailsViewModel>()
        val navigator = LocalNavigator.currentOrThrow
        val context = LocalContext.current

        LaunchedEffect(mealModel, mealDetailsModel) {
            viewModel.onArgumentsReceive(mealModel, mealDetailsModel)
        }

        Content(
            meal = viewModel.mealDetails.collectAsState().value,
            onBackButtonClick = navigator::pop,
            onSaveButtonClick = viewModel::onSaveButtonClick,
            onCategoryClick = {
                val screen = MealsScreen(category = CategoryModel(it, "", ""))
                navigator.push(screen)
            },
            onRegionClick = {
                val screen = MealsScreen(region = RegionModel(it))
                navigator.push(screen)
            },
            onYoutubeClick = context::openLink,
            onSourceClick = context::openLink,
        )
    }

    @Composable
    private fun Content(
        meal: MealDetailsModel,
        onBackButtonClick: () -> Unit,
        onSaveButtonClick: () -> Unit,
        onCategoryClick: (String) -> Unit,
        onRegionClick: (String) -> Unit,
        onYoutubeClick: (String) -> Unit,
        onSourceClick: (String) -> Unit,
    ) {
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 24.dp),
            modifier = Modifier
                .background(AppTheme.colorScheme.BG1)
                .fillMaxSize()
                .systemBarsPadding()
        ) {
            toolbar(
                meal = meal,
                onBackButtonClick = onBackButtonClick,
                onSaveButtonClick = onSaveButtonClick,
            )

            mealThumbnail(
                meal = meal,
            )

            mealName(
                meal = meal
            )

            labels(
                meal = meal,
                onCategoryClick = onCategoryClick,
                onRegionClick = onRegionClick
            )

            instructions(
                meal = meal,
            )

            ingredients(
                meal = meal,
            )

            externalLinks(
                meal = meal,
                onYoutubeClick = onYoutubeClick,
                onSourceClick = onSourceClick
            )
        }
    }

    @Preview(showBackground = true)
    @Composable
    private fun ContentPreview() {
        AppTheme(indication = ScaleIndicationNodeFactory) {
            Content(
                meal = MealDetailsModel(
                    id = "1234",
                    name = "La Omelet de Paris",
                    category = "Breakfast",
                    region = "France",
                    instructions = "Some long, very very long instructions " +
                            "on how to cook a particular meal",
                    thumbnail = "https://leitesculinaria.com/wp-content/uploads/2024/03/ham-and-cheese-omelet-1200.jpg",
                    youtubeUrl = "https://youtube.com",
                    sourceUrl = "https://google.com",
                    ingredients = listOf(
                        IngredientModel("", "Egg", "2"),
                        IngredientModel("", "Bacon", "20g"),
                        IngredientModel("", "Pepper", "5g")
                    ),
                ),
                onBackButtonClick = {},
                onSaveButtonClick = {},
                onCategoryClick = {},
                onRegionClick = {},
                onYoutubeClick = {},
                onSourceClick = {},
            )
        }
    }

    private fun LazyListScope.toolbar(
        meal: MealDetailsModel,
        onBackButtonClick: () -> Unit,
        onSaveButtonClick: () -> Unit,
    ) {
        item(key = "Toolbar") {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(
                    icon = painterResource(id = R.drawable.ic_back),
                    size = Size.S,
                    cornerType = CornerType.CIRCULAR,
                    keyColor = KeyColor.SECONDARY,
                    onClick = onBackButtonClick
                )

                Text(
                    text = stringResource(R.string.meal_details_title),
                    style = AppTheme.typography.S1,
                    modifier = Modifier.weight(1f)
                )

                val iconResId = if (meal.isSaved) R.drawable.ic_bookmark_fill else R.drawable.ic_bookmark

                CompositionLocalProvider(
                    LocalContentColor provides Red900
                ) {
                    IconButton(
                        icon = painterResource(id = iconResId),
                        size = Size.S,
                        cornerType = CornerType.CIRCULAR,
                        keyColor = KeyColor.SECONDARY,
                        onClick = onSaveButtonClick
                    )
                }
            }
        }
    }

    private fun LazyListScope.mealThumbnail(meal: MealDetailsModel) {
        if (meal.thumbnail.isBlank()) return

        var isImageLoading = true

        item(key = meal.thumbnail) {
            AsyncImage(
                model = meal.thumbnail,
                contentDescription = "MealImage",
                onLoading = { isImageLoading = true },
                onSuccess = { isImageLoading = false },
                onError = { isImageLoading = false },
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(top = 24.dp)
                    .fillMaxWidth()
                    .height(320.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(shimmerBrush(isImageLoading))
            )
        }
    }

    private fun LazyListScope.mealName(meal: MealDetailsModel) {
        item(key = meal.name) {
            Text(
                text = meal.name,
                style = AppTheme.typography.H5,
                modifier = Modifier.padding(top = 12.dp)
            )
        }
    }

    private fun LazyListScope.labels(
        meal: MealDetailsModel,
        onCategoryClick: (String) -> Unit,
        onRegionClick: (String) -> Unit,
    ) {
        if (meal.region.isBlank() && meal.category.isBlank()) return

        item(key = meal.category + meal.region) {
            CompositionLocalProvider(
                LocalContentColor provides AppTheme.colorScheme.T8
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .animateItem()
                        .fillMaxWidth()
                        .padding(top = 24.dp)
                ) {
                    val labelDefault = LabelData(
                        textStyle = AppTheme.typography.P4,
                        textColor = AppTheme.colorScheme.T8,
                        backgroundColor = AppTheme.colorScheme.BG4,
                        paddingValues = PaddingValues(horizontal = 20.dp, vertical = 6.dp),
                    )

                    Label(
                        labelData = labelDefault.copy(
                            text = meal.region,
                            icon = painterResource(id = R.drawable.ic_region),
                            onClick = { onRegionClick(meal.region) }
                        )
                    )

                    Label(
                        labelData = labelDefault.copy(
                            text = meal.category,
                            icon = painterResource(id = R.drawable.ic_category),
                            onClick = { onCategoryClick(meal.category) }
                        )
                    )
                }
            }
        }
    }

    private fun LazyListScope.instructions(meal: MealDetailsModel) {
        if (meal.instructions.isBlank()) return

        item(key = "InstructionsTitle") {
            Text(
                text = stringResource(R.string.meal_details_cooking_process),
                style = AppTheme.typography.S1,
                modifier = Modifier
                    .animateItem()
                    .padding(top = 32.dp)
            )
        }

        item(key = meal.instructions) {
            Text(
                text = meal.instructions,
                style = AppTheme.typography.P4,
                modifier = Modifier
                    .animateItem()
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )
        }
    }

    private fun LazyListScope.ingredients(meal: MealDetailsModel) {
        if (meal.ingredients.isEmpty()) return

        item(key = "IngredientsTitle") {
            Text(
                text = stringResource(id = R.string.meal_details_ingredients),
                style = AppTheme.typography.S1,
                modifier = Modifier
                    .animateItem()
                    .padding(
                        top = 32.dp,
                        bottom = 8.dp
                    )
            )
        }

        items(
            key = { it },
            items = meal.ingredients
        ) {
            IngredientItem(
                item = it,
                modifier = Modifier.animateItem()
            )
        }
    }

    private fun LazyListScope.externalLinks(
        meal: MealDetailsModel,
        onYoutubeClick: (String) -> Unit,
        onSourceClick: (String) -> Unit,
    ) {
        if (meal.youtubeUrl.isNullOrBlank() && meal.sourceUrl.isNullOrBlank()) return

        item(key = "ExternalLinksTitle") {
            Text(
                text = stringResource(id = R.string.meal_details_external_sources),
                style = AppTheme.typography.S1,
                modifier = Modifier
                    .padding(top = 32.dp)
            )
        }

        item(key = "ExternalLinksDescription") {
            Text(
                text = stringResource(id = R.string.meal_details_external_sources_description),
                style = AppTheme.typography.P4,
            )
        }

        item(key = meal.youtubeUrl + meal.sourceUrl) {
            CompositionLocalProvider(
                LocalContentColor provides Color.White
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth()
                ) {
                    meal.youtubeUrl?.let {
                        TextButton(
                            text = stringResource(id = R.string.meal_details_youtube),
                            icon = painterResource(id = R.drawable.ic_youtube),
                            backgroundColor = Red900,
                            modifier = Modifier.weight(1f),
                            onClick = { onYoutubeClick(it) }
                        )
                    }

                    meal.sourceUrl?.let {
                        TextButton(
                            text = stringResource(id = R.string.meal_details_website),
                            icon = painterResource(id = R.drawable.ic_link),
                            backgroundColor = AppTheme.colorScheme.BG10,
                            modifier = Modifier.weight(1f),
                            onClick = { onSourceClick(it) }
                        )
                    }
                }
            }
        }
    }
}