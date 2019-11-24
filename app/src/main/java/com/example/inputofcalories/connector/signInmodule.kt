package com.example.inputofcalories.connector

import com.example.inputofcalories.domain.user.SaveUserToLocalUseCase
import com.example.inputofcalories.domain.user.SaveUserToLocalUseCaseImpl
import com.example.inputofcalories.domain.auth.signin.SignInUserUseCase
import com.example.inputofcalories.domain.auth.signin.SignInUserUseCaseImpl
import com.example.inputofcalories.domain.auth.signin.validation.CheckSignInFieldsAreFilledUseCase
import com.example.inputofcalories.domain.auth.signin.validation.CheckSignInFieldsAreFilledUseCaseImpl
import com.example.inputofcalories.presentation.auth.signin.SignInViewModel
import com.example.inputofcalories.repo.user.SaveUserToDbRepo
import com.example.inputofcalories.repo.user.SaveUserToRoom
import com.example.inputofcalories.repo.auth.signin.SignInUserRepo
import com.example.inputofcalories.repo.auth.signin.SignInUserRepoImpl
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val singinmodule = module {
    single<SignInUserRepo> { SignInUserRepoImpl(get()) }

    single<SaveUserToDbRepo> { SaveUserToRoom(get()) }

    single<SignInUserUseCase> { SignInUserUseCaseImpl(get()) }

    single<SaveUserToLocalUseCase> { SaveUserToLocalUseCaseImpl(get()) }

    single<CheckSignInFieldsAreFilledUseCase> { CheckSignInFieldsAreFilledUseCaseImpl() }

    viewModel { SignInViewModel(get(), get(), get(), get()) }
}