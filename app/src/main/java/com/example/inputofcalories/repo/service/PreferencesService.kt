package com.example.inputofcalories.repo.service

interface PreferencesService {
    val preferencesFilePath: String
    fun <T> preference(key: String, defaultValue: T): T
    fun <T> putPreference(key: String, value: T)
    fun clearField(key: String)
    fun clearAll()
}

inline fun <reified T> PreferencesService.preferenceDelegate(
    key: String, default: T
) = PreferencesDelegate(key, default, this)