package com.example.inputofcalories.connector

import com.example.inputofcalories.common.rx.RxExecutor
import com.example.inputofcalories.common.rx.ThreadExecutor
import com.example.inputofcalories.common.rx.ThreadExecutorImpl
import org.koin.dsl.module

val rxModule = module {
    single {
        RxExecutor(get())
    }

    single<ThreadExecutor> {
        ThreadExecutorImpl()
    }
}