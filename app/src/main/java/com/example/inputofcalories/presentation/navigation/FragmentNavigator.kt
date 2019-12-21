package com.example.inputofcalories.presentation.navigation

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

object FragmentNavigator {
    fun openFragment(
        activity: AppCompatActivity,
        fragment: Fragment,
        containerViewId: Int,
        tag: String?) {
        activity
            .supportFragmentManager
            .beginTransaction()
            .replace(containerViewId, fragment, tag).commit()
    }
}