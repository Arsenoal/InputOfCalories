package com.example.inputofcalories.presentation.navigation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import java.io.Serializable

object ActivityNavigator {
    fun navigate(
        activity: AppCompatActivity,
        activityClassName: Class<out AppCompatActivity>) {
        activity.run {
            val intent = Intent(this, activityClassName)
            startActivity(intent)
        }
    }

    fun navigate(
        activity: AppCompatActivity,
        activityClassName: Class<out AppCompatActivity>,
        key: String,
        value: Serializable) {
        activity.run {
            val intent = Intent(this, activityClassName)
            intent.putExtra(key, value)
            startActivity(intent)
        }
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

    fun navigateAndFinishCurrent(
        activity: AppCompatActivity,
        activityClassName: Class<out AppCompatActivity>,
        key: String,
        value: Serializable) {
        activity.run {
            val intent = Intent(this, activityClassName)
            intent.putExtra(key, value)
            startActivity(intent)
            finish()
        }
    }

    fun navigateBack(activity: AppCompatActivity) {
        activity.onBackPressed()
    }
}