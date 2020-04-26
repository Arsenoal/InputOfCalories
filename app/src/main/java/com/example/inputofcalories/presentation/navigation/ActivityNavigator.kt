package com.example.inputofcalories.presentation.navigation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import java.io.Serializable

object ActivityNavigator {
    fun navigate(
        activity: AppCompatActivity,
        activityClassName: Class<out AppCompatActivity>) = with(activity) {

        val intent = Intent(this, activityClassName)
        startActivity(intent)
    }

    fun navigate(
        activity: AppCompatActivity,
        activityClassName: Class<out AppCompatActivity>,
        key: String,
        value: Serializable) = with(activity) {

        val intent = Intent(this, activityClassName)
        intent.putExtra(key, value)
        startActivity(intent)
    }

    fun navigateAndFinishCurrent(
        activity: AppCompatActivity,
        activityClassName: Class<out AppCompatActivity>) = with(activity) {

        val intent = Intent(this, activityClassName)
        startActivity(intent)
        finish()
    }

    fun navigateAndFinishCurrent(
        activity: AppCompatActivity,
        activityClassName: Class<out AppCompatActivity>,
        key: String,
        value: Serializable) = with(activity) {

        val intent = Intent(this, activityClassName)
        intent.putExtra(key, value)
        startActivity(intent)
        finish()
    }

    fun navigateAndClearStack(
        activity: AppCompatActivity,
        activityClassName: Class<out AppCompatActivity>
    ) = with(activity) {

        val intent = Intent(this, activityClassName)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }

    fun finish(activity: AppCompatActivity) {
        activity.finish()
    }
}