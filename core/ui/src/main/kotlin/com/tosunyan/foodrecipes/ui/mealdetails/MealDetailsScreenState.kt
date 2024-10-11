package com.tosunyan.foodrecipes.ui.mealdetails

import com.tosunyan.foodrecipes.model.MealDetailsModel

data class MealDetailsScreenState(
    val meal: MealDetailsModel = MealDetailsModel(),

    val isSharingOptionsDialogVisible: Boolean = false,

    val onBackButtonClick: () -> Unit = { },
    val onShareButtonClick: () -> Unit = { },
    val onSaveButtonClick: () -> Unit = { },
    val onCategoryClick: (String) -> Unit = { },
    val onRegionClick: (String) -> Unit = { },
    val onYoutubeClick: (String) -> Unit = { },
    val onSourceClick: (String) -> Unit = { },
    val onSharingOptionsDialogDismiss: () -> Unit = { },
)