package com.example.inputofcalories.connector

import com.example.inputofcalories.domain.registration.RegisterUserUseCase
import com.example.inputofcalories.domain.registration.RegisterUserUseCaseImpl
import com.example.inputofcalories.repo.common.service.UUIDGeneratorService
import com.example.inputofcalories.repo.common.service.UUIDGeneratorServiceImpl
import com.example.inputofcalories.repo.registration.RegisterUserRepo
import com.example.inputofcalories.repo.registration.RegisterUserRepoImpl
import com.example.inputofcalories.repo.registration.RegistrationService
import com.example.inputofcalories.repo.common.service.restapicore.RestApiCreator
import com.example.inputofcalories.repo.registration.idcreator.IdCreatorRepo
import com.example.inputofcalories.repo.registration.idcreator.IdCreatorRepoImpl
import org.koin.dsl.module

val registrationModule = module {
    single { get<RestApiCreator>().createApiService(RegistrationService::class.java) }

    single<UUIDGeneratorService> { UUIDGeneratorServiceImpl() }

    single<RegisterUserRepo> { RegisterUserRepoImpl(get()) }

    single<IdCreatorRepo> { IdCreatorRepoImpl(get()) }

    single<RegisterUserUseCase> { RegisterUserUseCaseImpl(get(), get()) }
}