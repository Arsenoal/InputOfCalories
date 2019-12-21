package com.example.inputofcalories.presentation

import android.app.Activity
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity

object KeyboardManager {
    fun hideKeyboard(activity: AppCompatActivity) {
        activity.run {
            val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager

            currentFocus?.let {
                imm.hideSoftInputFromWindow(it.windowToken, 0)
            }
        }
    }
}