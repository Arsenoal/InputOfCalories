package com.example.inputofcalories.connector

import com.example.inputofcalories.domain.managerflow.*
import com.example.inputofcalories.presentation.managerflow.home.UsersProviderViewModel
import com.example.inputofcalories.presentation.managerflow.home.UserStatusManipulatorViewModel
import com.example.inputofcalories.repo.adminflow.DowngradeUserToRegularRepo
import com.example.inputofcalories.repo.adminflow.DowngradeUserToRegularRepoImpl
import com.example.inputofcalories.repo.managerflow.GetUsersRepo
import com.example.inputofcalories.repo.managerflow.GetUsersRepoImpl
import com.example.inputofcalories.repo.managerflow.UpgradeUserToManagerRepo
import com.example.inputofcalories.repo.managerflow.UpgradeUserToManagerRepoImpl
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val managermodule = module {
    single<GetUsersRepo> {
        GetUsersRepoImpl(get())
    }

    single<GetUsersUseCase> {
        GetUsersUseCaseImpl(get())
    }

    viewModel {
        UsersProviderViewModel(get(), get())
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
        UserStatusManipulatorViewModel(get(), get())
    }
}