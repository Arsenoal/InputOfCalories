package com.example.inputofcalories.connector

import com.example.inputofcalories.domain.user.SaveUserToLocalUseCase
import com.example.inputofcalories.domain.user.SaveUserToLocalUseCaseImpl
import com.example.inputofcalories.domain.auth.signin.SignInUserUseCase
import com.example.inputofcalories.domain.auth.signin.SignInUserUseCaseImpl
import com.example.inputofcalories.domain.auth.signin.validation.CheckSignInFieldsAreFilledUseCase
import com.example.inputofcalories.domain.auth.signin.validation.CheckSignInFieldsAreFilledUseCaseImpl
import com.example.inputofcalories.presentation.auth.signin.SignInViewModel
import com.example.inputofcalories.repo.auth.signin.GetUserByEmailFirestore
import com.example.inputofcalories.repo.auth.signin.GetUserByEmailRepo
import com.example.inputofcalories.repo.user.SaveUserToDbRepo
import com.example.inputofcalories.repo.user.SaveUserToRoom
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val singinmodule = module {
    single<GetUserByEmailRepo> { GetUserByEmailFirestore(get()) }

    single<SaveUserToDbRepo> { SaveUserToRoom(get()) }

    single<SignInUserUseCase> { SignInUserUseCaseImpl(get()) }

    single<SaveUserToLocalUseCase> { SaveUserToLocalUseCaseImpl(get()) }

    single<CheckSignInFieldsAreFilledUseCase> { CheckSignInFieldsAreFilledUseCaseImpl() }

    viewModel { SignInViewModel(get(), get(), get(), get()) }
}