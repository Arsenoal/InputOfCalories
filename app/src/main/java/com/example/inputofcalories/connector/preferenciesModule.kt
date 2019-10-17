package com.example.inputofcalories.connector

import com.example.inputofcalories.repo.common.service.preferences.PreferencesDefaultService
import com.example.inputofcalories.repo.common.service.preferences.PreferencesService
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val preferencesModule = module {
    single<PreferencesService> { PreferencesDefaultService(androidApplication()) }
}