package com.example.inputofcalories.connector

import android.content.Context
import com.example.inputofcalories.domain.user.IofUser
import com.example.inputofcalories.domain.user.UserUseCase
import com.example.inputofcalories.repo.user.UserInMemoryCacheRepo
import com.example.inputofcalories.repo.user.UserRepo
import org.koin.dsl.module

val usermodule = module {
    single<UserRepo> {
        UserInMemoryCacheRepo()
    }

    single<UserUseCase> {
        IofUser(get())
    }
}