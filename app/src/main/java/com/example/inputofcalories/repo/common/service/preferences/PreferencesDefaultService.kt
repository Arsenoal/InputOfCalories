package com.example.inputofcalories.repo.common.service.preferences

import android.content.Context

class PreferencesDefaultService(private val context: Context) :
    PreferencesBaseService(context) {
    override val preferencesFilePath: String
        get() = "${context.packageName}_preferences"
}