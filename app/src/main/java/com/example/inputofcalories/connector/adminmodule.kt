package com.example.inputofcalories.connector

import com.example.inputofcalories.domain.adminflow.*
import com.example.inputofcalories.domain.adminflow.UpgradeUserToManagerUseCase
import com.example.inputofcalories.domain.adminflow.DowngradeUserToRegularUseCase
import com.example.inputofcalories.presentation.adminflow.home.AdminUserStatusManipulatorViewModel
import com.example.inputofcalories.presentation.adminflow.home.AllUsersProviderViewModel
import com.example.inputofcalories.repo.adminflow.*
import com.example.inputofcalories.repo.adminflow.UpgradeUserToManagerRepo
import com.example.inputofcalories.repo.adminflow.UpgradeUserToManagerRepoImpl
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

val adminmodule = module {

    //users provider
    single<GetAllUsersRepo> {
        GetAllUsersRepoImpl(get())
    }

    single<GetAllUsersUseCase> {
        GetAllUsersUseCaseImpl(get())
    }

    viewModel {
        AllUsersProviderViewModel(get(), get{ parametersOf(null) })
    }

    //user status manipulator
    single<UpgradeUserToAdminRepo> {
        UpgradeUserToAdminRepoImpl(get())
    }

    single<UpgradeUserToAdminUseCase> {
        UpgradeUserToAdminUseCaseImpl(get())
    }

    single<DowngradeUserToManagerRepo> {
        DowngradeUserToManagerRepoImpl(get())
    }

    single<DowngradeUserToManagerUseCase> {
        DowngradeUserToManagerUseCaseImpl(get())
    }
    single<UpgradeUserToManagerRepo> {
        UpgradeUserToManagerRepoImpl(get())
    }

    single<UpgradeUserToManagerUseCase> {
        UpgradeUserToManagerUseCaseImpl(get())
    }

    single<DowngradeUserToRegularRepo> {
        DowngradeUserToRegularRepoImpl(get())
    }

    single<DowngradeUserToRegularUseCase> {
        DowngradeUserToRegularUseCaseImpl(get())
    }

    viewModel {
        AdminUserStatusManipulatorViewModel(get(), get(), get(), get())
    }
}