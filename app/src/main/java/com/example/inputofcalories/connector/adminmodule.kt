package com.example.inputofcalories.connector

import com.example.inputofcalories.domain.adminflow.*
import com.example.inputofcalories.repo.adminflow.*
import com.example.inputofcalories.presentation.adminflow.home.viewmodel.AdminUserStatusManipulatorViewModel
import com.example.inputofcalories.presentation.adminflow.home.viewmodel.AllUsersProviderViewModel

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val adminmodule = module {

    //users provider
    single<AdminRepo> {
        AdminFirestore(get())
    }

    single<AdminFlowUseCase> {
        IofAdminFlow(get())
    }

    viewModel { params ->
        val userId: String = params[0]
        AllUsersProviderViewModel(
            userId,
            get()
        )
    }

    viewModel {
        AdminUserStatusManipulatorViewModel(
            get()
        )
    }
}