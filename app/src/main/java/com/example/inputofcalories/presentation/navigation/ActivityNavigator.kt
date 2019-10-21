package com.example.inputofcalories.presentation.navigation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

object ActivityNavigator {
    fun navigate(
        activity: AppCompatActivity,
        activityClassName: Class<out AppCompatActivity>) {
        activity.run {
            val intent = Intent(this, activityClassName)
            startActivity(intent)
        }
    }

    fun navigateBack(activity: AppCompatActivity) {
        activity.onBackPressed()
    }

    fun navigateAndFinishCurrent(
        activity: AppCompatActivity,
        activityClassName: Class<out AppCompatActivity>) {
        activity.run {
            val intent = Intent(this, activityClassName)
            startActivity(intent)
            finish()
        }
    }
}