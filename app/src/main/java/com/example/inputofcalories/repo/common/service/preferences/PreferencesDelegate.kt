package com.example.inputofcalories.repo.common.service.preferences

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class PreferencesDelegate<T>(
    private val key: String,
    private val defaultValue: T,
    private val preferencesServiceAPI: PreferencesService
) : ReadWriteProperty<Any, T> {

    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        return preferencesServiceAPI.preference(key, defaultValue)
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        preferencesServiceAPI.putPreference(key, value)
    }
}