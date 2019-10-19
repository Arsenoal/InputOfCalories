package com.example.inputofcalories.connector

import com.example.inputofcalories.domain.registration.RegisterUserUseCase
import com.example.inputofcalories.domain.registration.RegisterUserUseCaseImpl
import com.example.inputofcalories.presentation.registration.RegisterUserViewModel
import com.example.inputofcalories.repo.common.service.UUIDGeneratorService
import com.example.inputofcalories.repo.common.service.UUIDGeneratorServiceImpl
import com.example.inputofcalories.repo.registration.RegisterUserRepo
import com.example.inputofcalories.repo.registration.RegisterUserRepoImpl
import com.example.inputofcalories.repo.registration.UpdateUserRepo
import com.example.inputofcalories.repo.registration.UpdateUserRepoImpl
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val registrationModule = module {
    single<UUIDGeneratorService> { UUIDGeneratorServiceImpl() }

    single<RegisterUserRepo> { RegisterUserRepoImpl(get(), get()) }

    single<UpdateUserRepo> { UpdateUserRepoImpl(get()) }

    single<RegisterUserUseCase> { RegisterUserUseCaseImpl(get()) }

    viewModel { RegisterUserViewModel(get()) }
}