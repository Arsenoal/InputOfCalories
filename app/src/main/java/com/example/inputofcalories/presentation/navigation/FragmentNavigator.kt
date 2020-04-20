package com.example.inputofcalories.presentation.navigation

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

object FragmentNavigator {
    fun openOrReplace(
        activity: AppCompatActivity,
        fragment: Fragment,
        containerViewId: Int,
        tag: String? = null) = with(activity.supportFragmentManager) {

        val fragmentFromBackStack = findFragmentByTag(tag)

        if(fragmentFromBackStack != null)
            beginTransaction().replace(containerViewId, fragmentFromBackStack, tag).commit()
        else
            beginTransaction().addToBackStack(tag).replace(containerViewId, fragment, tag).commit()

    }

    fun remove(activity: AppCompatActivity,
        fragment: Fragment) = with(activity.supportFragmentManager) { beginTransaction().remove(fragment).commit() }
}