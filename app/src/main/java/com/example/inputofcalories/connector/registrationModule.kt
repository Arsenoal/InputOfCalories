package com.example.inputofcalories.connector

import com.example.inputofcalories.domain.registration.RegisterUserUseCase
import com.example.inputofcalories.domain.registration.RegisterUserUseCaseImpl
import com.example.inputofcalories.repo.registration.RegisterUserRepo
import com.example.inputofcalories.repo.registration.RegisterUserRepoImpl
import com.example.inputofcalories.repo.registration.RegistrationService
import com.example.inputofcalories.repo.service.restapicore.RestApiCreator
import org.koin.dsl.module

val registrationModule = module {
    single { get<RestApiCreator>().createApiService(RegistrationService::class.java) }

    single<RegisterUserRepo> { RegisterUserRepoImpl(get()) }

    single<RegisterUserUseCase> { RegisterUserUseCaseImpl(get()) }
}