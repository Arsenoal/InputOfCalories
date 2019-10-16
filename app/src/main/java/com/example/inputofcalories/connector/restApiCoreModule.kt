package com.example.inputofcalories.connector

import com.example.inputofcalories.common.extensions.empty
import com.example.inputofcalories.repo.service.restapicore.ApiProviderConfig
import com.example.inputofcalories.repo.service.restapicore.RestApiCreator
import org.koin.dsl.module

val restApiCoreModule = module {

    single { RestApiCreator(get()) }

    single {
        ApiProviderConfig(baseUrl = String.empty())
    }
}