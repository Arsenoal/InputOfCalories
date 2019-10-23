package com.example.inputofcalories.connector

import com.example.inputofcalories.domain.managerflow.GetRegularUsersUseCase
import com.example.inputofcalories.domain.managerflow.GetRegularUsersUseCaseImpl
import com.example.inputofcalories.presentation.managerflow.home.RegularUsersProviderViewModel
import com.example.inputofcalories.repo.managerflow.GetUsersRepo
import com.example.inputofcalories.repo.managerflow.GetUsersRepoImpl
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val regularmodule = module {
    single<GetUsersRepo> {
        GetUsersRepoImpl(get())
    }

    single<GetRegularUsersUseCase> {
        GetRegularUsersUseCaseImpl(get())
    }

    viewModel {
        RegularUsersProviderViewModel(get())
    }
}