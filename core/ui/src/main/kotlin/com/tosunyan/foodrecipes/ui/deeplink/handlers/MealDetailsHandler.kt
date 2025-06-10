package com.tosunyan.foodrecipes.ui.deeplink.handlers

import android.net.Uri
import cafe.adriel.voyager.navigator.Navigator
import com.tosunyan.foodrecipes.model.MealModel
import com.tosunyan.foodrecipes.ui.deeplink.DeepLinkHandler
import com.tosunyan.foodrecipes.ui.deeplink.DeepLinkOwner
import com.tosunyan.foodrecipes.ui.deeplink.DeepLinkValidator
import com.tosunyan.foodrecipes.ui.screens.mealdetails.MealDetailsScreen

@JvmInline
internal value class MealId(val value: String)

internal class MealDetailsHandler(
    private val navigator: Navigator,
) : DeepLinkHandler<MealId> {

    override val owner = DeepLinkOwner { intentData: Uri? ->
        "meal" in intentData?.pathSegments.orEmpty()
    }

    override val validator = DeepLinkValidator<MealId> { intentData: Uri ->
        val mealId = intentData.lastPathSegment ?: return@DeepLinkValidator false
        return@DeepLinkValidator mealId.toIntOrNull() != null
    }

    override fun onDeepLinkAction(deepLinkData: MealId) {
        val mealModel = MealModel(id = deepLinkData.value)
        val screen = MealDetailsScreen(mealModel = mealModel)

        val isDetailsOpened = navigator.lastItem.key == deepLinkData.value
        if (!isDetailsOpened) {
            navigator.push(screen)
        }
    }
}