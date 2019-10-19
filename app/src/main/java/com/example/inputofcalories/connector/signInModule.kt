package com.example.inputofcalories.connector

import com.example.inputofcalories.domain.auth.signin.SignInUserUseCase
import com.example.inputofcalories.domain.auth.signin.SignInUserUseCaseImpl
import com.example.inputofcalories.presentation.auth.signin.SignInViewModel
import com.example.inputofcalories.repo.auth.signin.SignInUserRepo
import com.example.inputofcalories.repo.auth.signin.SignInUserRepoImpl
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val singinmodule = module {
    single<SignInUserRepo> { SignInUserRepoImpl(get()) }

    single<SignInUserUseCase> { SignInUserUseCaseImpl(get()) }

    viewModel { SignInViewModel(get()) }
}