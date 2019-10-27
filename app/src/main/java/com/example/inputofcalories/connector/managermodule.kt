package com.example.inputofcalories.connector

import com.example.inputofcalories.domain.managerflow.*
import com.example.inputofcalories.presentation.managerflow.home.UsersProviderViewModel
import com.example.inputofcalories.presentation.managerflow.home.ManagerUserStatusManipulatorViewModel
import com.example.inputofcalories.repo.managerflow.DowngradeManagerToRegularUserRepo
import com.example.inputofcalories.repo.managerflow.DowngradeManagerToRegularUserRepoImpl
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

    viewModel { params->
        val userId: String = params[0]
        UsersProviderViewModel(userId, get())
    }

    single<UpgradeUserToManagerRepo> {
        UpgradeUserToManagerRepoImpl(get())
    }

    single<UpgradeUserToManagerUseCase> {
        UpgradeUserToManagerUseCaseImpl(get())
    }

    single<DowngradeManagerToRegularUserRepo> {
        DowngradeManagerToRegularUserRepoImpl(get())
    }

    single<DowngradeUserToRegularUseCase> {
        DowngradeUserToRegularUseCaseImpl(get())
    }

    viewModel {
        ManagerUserStatusManipulatorViewModel(get(), get())
    }
}