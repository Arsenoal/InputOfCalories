package com.example.inputofcalories.repo.common.service.preferences

import android.content.Context

abstract class PreferencesBaseService(private val context: Context) :
    PreferencesService {

    private val sharedPreferences by lazy {
        context.getSharedPreferences(preferencesFilePath, Context.MODE_PRIVATE)
    }

    override fun clearField(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }

    override fun clearAll() {
        sharedPreferences.edit().clear().apply()
    }

    override fun <T> preference(key: String, defaultValue: T): T = sharedPreferences.run {
        @Suppress("UNCHECKED_CAST", "IMPLICIT_CAST_TO_ANY")
        when (defaultValue) {
            is Boolean -> getBoolean(key, defaultValue)
            is String -> getString(key, defaultValue)
            is Int -> getInt(key, defaultValue)
            is Float -> getFloat(key, defaultValue)
            is Long -> getLong(key, defaultValue)
            is Set<*> -> getStringSet(key, defaultValue as Set<String>)
            else -> throw IllegalArgumentException("value type is not supported")
        } as T
    }

    override fun <T> putPreference(key: String, value: T) {
        @Suppress("UNCHECKED_CAST")
        with(sharedPreferences.edit()) {
            when (value) {
                is Boolean -> putBoolean(key, value)
                is String -> putString(key, value)
                is Int -> putInt(key, value)
                is Float -> putFloat(key, value)
                is Long -> putLong(key, value)
                is Set<*> -> putStringSet(key, value as Set<String>)
                else -> throw IllegalArgumentException("value type is not supported")
            }.apply()
        }
    }
}