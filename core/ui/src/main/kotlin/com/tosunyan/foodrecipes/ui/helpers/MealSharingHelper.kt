package com.tosunyan.foodrecipes.ui.helpers

import android.content.Context
import android.content.Intent
import com.tosunyan.foodrecipes.model.MealDetailsModel
import com.tosunyan.foodrecipes.model.SaveableMeal
import com.tosunyan.foodrecipes.ui.R
import com.tosunyan.foodrecipes.ui.shareoptions.SharingOption
import com.tosunyan.foodrecipes.ui.utils.launchIntentChooser

class MealSharingHelper {

    fun shareMeal(
        meal: MealDetailsModel,
        sharingOption: SharingOption,
        context: Context,
    ) {
        val text = when (sharingOption) {
            SharingOption.Link -> generateLink(meal)
            SharingOption.Text -> generateRecipeContent(meal, context)
        }

        context.launchIntentChooser(Intent.EXTRA_TEXT to text)
    }

    private fun generateLink(meal: SaveableMeal): String {
        return BASE_URL + meal.id
    }

    private fun generateRecipeContent(
        meal: MealDetailsModel,
        context: Context,
    ): String {
        return """
${meal.name}
#${meal.region}
#${meal.category}

${context.getString(R.string.meal_details_cooking_process)}
${meal.instructions}

${context.getString(R.string.meal_details_ingredients)}
${meal.ingredients.joinToString("\n") { "- ${it.quantity} ${it.name}" }}
                """.trimIndent()
    }

    companion object {

        private const val BASE_URL = "https://foodrecipes.com/meal/"
    }
}