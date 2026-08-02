package com.tosunyan.foodrecipes.main.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.inconceptlabs.designsystem.components.core.Text
import com.inconceptlabs.designsystem.theme.AppTheme
import com.tosunyan.foodrecipes.main.R
import com.tosunyan.foodrecipes.ui.theme.FoodRecipesTheme

class ProfileScreen : Tab {

    override val options: TabOptions
        @Composable
        get() = TabOptions(
            index = 3u,
            title = stringResource(id = R.string.navigation_item_profile),
        )

    @Composable
    override fun Content() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.colorScheme.BG1)
                .padding(
                    start = 20.dp,
                    end = 20.dp,
                    top = 24.dp
                )
        ) {
            Text(
                text = stringResource(id = R.string.navigation_item_profile),
                style = AppTheme.typography.H4,
            )
        }
    }

    @Preview
    @Composable
    private fun ContentPreview() {
        FoodRecipesTheme {
            Content()
        }
    }
}