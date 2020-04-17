package com.example.inputofcalories.presentation.common

import android.app.Activity
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager

object KeyboardManager {
    fun hideKeyboard(activity: Activity) {
        activity.run {
            val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager

            currentFocus?.let {
                imm.hideSoftInputFromWindow(it.windowToken, 0)
            }
        }
    }

    fun showKeyboard(window: Window) {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
    }
}