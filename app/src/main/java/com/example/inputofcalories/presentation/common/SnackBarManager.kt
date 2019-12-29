package com.example.inputofcalories.presentation.common

import android.view.View
import com.google.android.material.snackbar.Snackbar

object SnackBarManager {
    fun showSnackShort(view: View, text: String) {
        Snackbar.make(view, text, Snackbar.LENGTH_SHORT).show()
    }

    fun showSnackLong(view: View, text: String) {
        Snackbar.make(view, text, Snackbar.LENGTH_LONG).show()
    }

    fun showSnackIndefinite(view: View, text: String) {
        Snackbar.make(view, text, Snackbar.LENGTH_INDEFINITE).show()
    }
}