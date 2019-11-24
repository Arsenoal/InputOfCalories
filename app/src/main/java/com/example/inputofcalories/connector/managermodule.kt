package com.example.inputofcalories.connector

import com.example.inputofcalories.domain.managerflow.*
import com.example.inputofcalories.presentation.managerflow.home.UsersProviderViewModel
import com.example.inputofcalories.presentation.managerflow.home.ManagerUserStatusManipulatorViewModel
import com.example.inputofcalories.repo.managerflow.DowngradeManagerToRegularUserRepo
import com.example.inputofcalories.repo.managerflow.DowngradeManagerToRegularUserFirestore
import com.example.inputofcalories.repo.managerflow.GetUsersRepo
import com.example.inputofcalories.repo.managerflow.GetUsersFirestore
import com.example.inputofcalories.repo.managerflow.UpgradeUserToManagerRepo
import com.example.inputofcalories.repo.managerflow.UpgradeUserToManagerFirestore
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val managermodule = module {
    single<GetUsersRepo> {
        GetUsersFirestore(get())
    }

    single<GetUsersUseCase> {
        GetUsersUseCaseImpl(get())
    }

    viewModel { params->
        val userId: String = params[0]
        UsersProviderViewModel(userId, get())
    }

    single<UpgradeUserToManagerRepo> {
        UpgradeUserToManagerFirestore(get())
    }

    single<UpgradeUserToManagerUseCase> {
        UpgradeUserToManagerUseCaseImpl(get())
    }

    single<DowngradeManagerToRegularUserRepo> {
        DowngradeManagerToRegularUserFirestore(get())
    }

    single<DowngradeUserToRegularUseCase> {
        DowngradeUserToRegularUseCaseImpl(get())
    }

    viewModel {
        ManagerUserStatusManipulatorViewModel(get(), get())
    }
}