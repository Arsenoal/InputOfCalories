package com.example.inputofcalories.presentation.navigation

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

object FragmentNavigator {
    fun openFragment(
        activity: AppCompatActivity,
        fragment: Fragment,
        containerViewId: Int,
        tag: String? = null) {

        activity.supportFragmentManager.run {
            val fragmentFromBackStack = findFragmentByTag(tag)

            if(fragmentFromBackStack != null) {
                beginTransaction()
                    .replace(containerViewId, fragmentFromBackStack, tag)
                    .commit()
            } else {
                beginTransaction()
                    .addToBackStack(tag)
                    .replace(containerViewId, fragment, tag)
                    .commit()
            }
        }
    }
}