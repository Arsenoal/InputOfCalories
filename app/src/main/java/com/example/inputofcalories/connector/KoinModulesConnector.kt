package com.example.inputofcalories.connector

import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class KoinModulesConnector {
    companion object {
        fun connect(context: Context) {
            startKoin {
                androidContext(context)

                modules(listOf(
                    preferencesModule,
                    firebasemodule,
                    userdbmodule(context),
                    authModule,
                    mealsmodule,
                    managermodule,
                    adminmodule,
                    launchermodule
                ))
            }
        }
    }
}