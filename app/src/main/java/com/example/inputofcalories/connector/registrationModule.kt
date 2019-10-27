package com.example.inputofcalories.connector

import com.example.inputofcalories.domain.auth.registration.*
import com.example.inputofcalories.domain.auth.registration.validation.*
import com.example.inputofcalories.presentation.auth.registration.RegisterUserViewModel
import com.example.inputofcalories.repo.common.service.UUIDGeneratorService
import com.example.inputofcalories.repo.common.service.UUIDGeneratorServiceImpl
import com.example.inputofcalories.repo.auth.registration.RegisterUserRepo
import com.example.inputofcalories.repo.auth.registration.RegisterUserRepoImpl
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val registrationModule = module {
    single<UUIDGeneratorService> { UUIDGeneratorServiceImpl() }

    single<RegisterUserRepo> { RegisterUserRepoImpl(get(), get()) }

    single<RegisterUserUseCase> { RegisterUserUseCaseImpl(get()) }

    single<CheckEmailFormatValidUseCase> {
        CheckEmailFormatValidUseCaseImpl()
    }

    single<CheckPasswordsMatchesUseCase> {
        CheckPasswordsMatchesUseCaseImpl()
    }

    single<CheckRegistrationFieldsAreFilledUseCase> {
        CheckRegistrationFieldsAreFilledUseCaseImpl()
    }

    viewModel { RegisterUserViewModel(get(), get(), get(), get()) }
}