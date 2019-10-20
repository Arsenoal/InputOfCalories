package com.example.inputofcalories.connector

import android.content.Context
import com.example.inputofcalories.repo.db.local.user.UserRoomDatabase
import org.koin.dsl.module

fun userdbmodule(context: Context) = module {
    single {
        UserRoomDatabase.getInstance(context)
    }

    single {
        get<UserRoomDatabase>().userDao()
    }
}