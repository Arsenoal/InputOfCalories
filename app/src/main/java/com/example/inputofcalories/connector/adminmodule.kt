package com.example.inputofcalories.connector

import com.example.inputofcalories.domain.adminflow.*
import com.example.inputofcalories.presentation.adminflow.home.AdminViewModel
import com.example.inputofcalories.repo.adminflow.*
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val adminmodule = module {

    single<AdminRepo> { AdminFirestore(get()) }

    single<AdminFlowUseCase> { IofAdminFlow(get(), get()) }

    viewModel { AdminViewModel(get()) }
}