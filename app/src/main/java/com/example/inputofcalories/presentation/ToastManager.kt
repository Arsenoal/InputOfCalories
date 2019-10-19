package com.example.inputofcalories.presentation

import android.content.Context
import android.widget.Toast

object ToastManager {
    fun showToastShort(
        context: Context,
        message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}