package com.example.inputofcalories.connector

import com.example.inputofcalories.domain.launch.GetUserRegistrationStatusUseCase
import com.example.inputofcalories.domain.launch.GetUserRegistrationStatusUseCaseImpl
import com.example.inputofcalories.presentation.launch.LauncherViewModel
import com.example.inputofcalories.repo.launch.RegistrationStateProviderRepo
import com.example.inputofcalories.repo.launch.RegistrationStateProviderRepoImpl
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val launchModule = module {
    single<RegistrationStateProviderRepo> {
        RegistrationStateProviderRepoImpl(get())
    }

    single<GetUserRegistrationStatusUseCase> {
        GetUserRegistrationStatusUseCaseImpl(get())
    }

    viewModel {
        LauncherViewModel(get())
    }
}