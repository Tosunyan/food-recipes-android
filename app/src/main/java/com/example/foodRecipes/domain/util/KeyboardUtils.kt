package com.example.foodRecipes.domain.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

object KeyboardUtils {

    fun hideKeyboard(context: Context, view: View) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun showKeyboard(context: Context, view: View?) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.showSoftInput(view, InputMethodManager.SHOW_FORCED)
    }
}