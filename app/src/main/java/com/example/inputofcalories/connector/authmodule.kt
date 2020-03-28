package com.example.inputofcalories.connector

import com.example.inputofcalories.domain.auth.AuthUseCase
import com.example.inputofcalories.domain.auth.IofAuth
import com.example.inputofcalories.domain.auth.IofAuthValidation
import com.example.inputofcalories.domain.auth.validation.AuthValidationUseCase
import com.example.inputofcalories.domain.user.SaveUserToLocalUseCase
import com.example.inputofcalories.domain.user.SaveUserToLocalUseCaseImpl
import com.example.inputofcalories.presentation.auth.registration.RegisterUserViewModel
import com.example.inputofcalories.presentation.auth.signin.SignInViewModel
import com.example.inputofcalories.repo.auth.AuthFirestore
import com.example.inputofcalories.repo.auth.AuthRepo
import com.example.inputofcalories.repo.auth.GetAllUsersFirestore
import com.example.inputofcalories.repo.auth.GetAllUsersRepo
import com.example.inputofcalories.repo.service.SHACreatorService
import com.example.inputofcalories.repo.service.SHAViaSecretKeyCreatorService
import com.example.inputofcalories.repo.user.SaveUserToDbRepo
import com.example.inputofcalories.repo.user.SaveUserToRoom
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val authModule = module {
    single<SHACreatorService> { SHAViaSecretKeyCreatorService() }

    single<AuthRepo> { AuthFirestore(get(), get()) }

    single<GetAllUsersRepo> { GetAllUsersFirestore(get()) }

    single<AuthUseCase> { IofAuth(get(), get()) }

    single<AuthValidationUseCase> { IofAuthValidation() }

    viewModel { RegisterUserViewModel(get(), get()) }

    single<SaveUserToDbRepo> { SaveUserToRoom(get()) }

    single<SaveUserToLocalUseCase> { SaveUserToLocalUseCaseImpl(get()) }

    viewModel { SignInViewModel(get(), get(), get()) }
}