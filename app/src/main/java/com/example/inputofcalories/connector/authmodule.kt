package com.example.inputofcalories.connector

import com.example.inputofcalories.domain.auth.AuthUseCase
import com.example.inputofcalories.domain.auth.IofAuth
import com.example.inputofcalories.domain.auth.IofAuthValidation
import com.example.inputofcalories.domain.auth.validation.AuthValidationUseCase
import com.example.inputofcalories.presentation.auth.AuthViewModel
import com.example.inputofcalories.repo.auth.AuthFirestore
import com.example.inputofcalories.repo.auth.AuthRepo
import com.example.inputofcalories.repo.auth.GetAllUsersFirestore
import com.example.inputofcalories.repo.auth.GetAllUsersRepo
import com.example.inputofcalories.repo.service.EncoderService
import com.example.inputofcalories.repo.service.IoCEncoderService
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val authModule = module {
    single<EncoderService> { IoCEncoderService() }

    single<AuthRepo> { AuthFirestore(get(), get()) }

    single<GetAllUsersRepo> { GetAllUsersFirestore(get()) }

    single<AuthUseCase> { IofAuth(get()) }

    single<AuthValidationUseCase> { IofAuthValidation() }

    viewModel { AuthViewModel(get(), get(), get()) }
}