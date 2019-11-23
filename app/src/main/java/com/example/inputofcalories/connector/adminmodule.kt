package com.example.inputofcalories.connector

import com.example.inputofcalories.domain.adminflow.*
import com.example.inputofcalories.presentation.adminflow.home.AdminUserStatusManipulatorViewModel
import com.example.inputofcalories.presentation.adminflow.home.AllUsersProviderViewModel
import com.example.inputofcalories.repo.adminflow.*
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val adminmodule = module {

    //users provider
    single<GetAllUsersRepo> {
        GetAllUsersRepoImpl(get())
    }

    single<GetAllUsersUseCase> {
        GetAllUsersUseCaseImpl(get())
    }

    viewModel { params ->
        val userId: String = params[0]
        AllUsersProviderViewModel(userId, get())
    }

    //user status manipulator
    single<AdminUserStatusManipulatorRepo> {
        AdminUserStatusManipulatorFirestore(get())
    }

    single<AdminUserStatusManipulatorUseCase> {
        AdminUserStatusManipulatorUseCaseImpl(get())
    }

    viewModel {
        AdminUserStatusManipulatorViewModel(get())
    }
}