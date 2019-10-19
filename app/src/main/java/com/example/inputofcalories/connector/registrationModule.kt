package com.example.inputofcalories.connector

import com.example.inputofcalories.domain.auth.registration.RegisterUserUseCase
import com.example.inputofcalories.domain.auth.registration.RegisterUserUseCaseImpl
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

    viewModel { RegisterUserViewModel(get()) }
}