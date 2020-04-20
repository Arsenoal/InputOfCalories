package com.example.inputofcalories.connector

import com.example.inputofcalories.domain.managerflow.*
import com.example.inputofcalories.presentation.managerflow.home.ManagerViewModel
import com.example.inputofcalories.repo.managerflow.ManagerFirestore
import com.example.inputofcalories.repo.managerflow.ManagerRepo
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val managermodule = module {

    single<ManagerRepo> {
        ManagerFirestore(get())
    }

    single<MangerFlowUseCase> {
        IofManagerFlow(get(), get())
    }

    viewModel {
        ManagerViewModel(get())
    }
}