package com.example.inputofcalories.presentation

import android.app.Application
import com.example.inputofcalories.connector.KoinModulesConnector

class InputOfCaloriesApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        KoinModulesConnector.connect(this.applicationContext)
    }
}