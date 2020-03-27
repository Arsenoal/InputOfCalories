package com.example.inputofcalories.connector

import com.example.inputofcalories.domain.managerflow.*
import com.example.inputofcalories.presentation.managerflow.home.UsersProviderViewModel
import com.example.inputofcalories.presentation.managerflow.home.ManagerUserStatusManipulatorViewModel
import com.example.inputofcalories.repo.managerflow.ManagerFirestore
import com.example.inputofcalories.repo.managerflow.ManagerRepo
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val managermodule = module {

    single<ManagerRepo> {
        ManagerFirestore(get())
    }

    single<MangerFlowUseCase> {
        IofManagerFlow(get())
    }

    viewModel { params->
        val userId: String = params[0]
        UsersProviderViewModel(userId, get())
    }

    viewModel {
        ManagerUserStatusManipulatorViewModel(get())
    }
}