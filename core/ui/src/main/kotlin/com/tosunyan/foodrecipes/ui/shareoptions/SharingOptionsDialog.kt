package com.tosunyan.foodrecipes.ui.shareoptions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.inconceptlabs.designsystem.components.core.Text
import com.inconceptlabs.designsystem.theme.AppTheme
import com.inconceptlabs.designsystem.theme.attributes.KeyColor
import com.tosunyan.foodrecipes.model.MealDetailsModel
import com.tosunyan.foodrecipes.ui.R
import com.tosunyan.foodrecipes.ui.components.listitem.ListItem
import com.tosunyan.foodrecipes.ui.helpers.MealSharingHelper

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SharingOptionsBottomSheet(
    mealDetails: MealDetailsModel,
    onDismiss: () -> Unit,
) {
    val context = LocalContext.current

    ModalBottomSheet(
        sheetState = rememberModalBottomSheetState(),
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        containerColor = AppTheme.colorScheme.BG2,
    ) {
        Column(
            modifier = Modifier
                .navigationBarsPadding()
                .padding(start = 20.dp, end = 20.dp, bottom = 24.dp),
        ) {
            Text(
                text = stringResource(R.string.sharing_options_title),
                style = AppTheme.typography.S1,
            )

            ListItem(
                title = stringResource(R.string.sharing_option_link_title),
                subtitle = stringResource(R.string.sharing_option_link_description),
                startIcon = painterResource(R.drawable.ic_link),
                keyColor = KeyColor.SECONDARY,
                modifier = Modifier.padding(top = 16.dp),
                onClick = {
                    onDismiss()
                    MealSharingHelper.shareMeal(mealDetails, SharingOption.Link, context)
                }
            )

            ListItem(
                title = stringResource(R.string.sharing_option_text_title),
                subtitle = stringResource(R.string.sharing_option_text_description),
                startIcon = painterResource(R.drawable.ic_text),
                keyColor = KeyColor.SECONDARY,
                onClick = {
                    onDismiss()
                    MealSharingHelper.shareMeal(mealDetails, SharingOption.Text, context)
                }
            )

            Text(
                text = stringResource(R.string.sharing_option_link_note),
                style = AppTheme.typography.P5,
                color = AppTheme.colorScheme.T7,
                modifier = Modifier
                    .padding(top = 24.dp)
            )
        }
    }
}