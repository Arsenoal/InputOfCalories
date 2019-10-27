package com.example.inputofcalories.connector

import com.example.inputofcalories.domain.launch.InvalidateLocalDataOnAppOpenUseCase
import com.example.inputofcalories.domain.launch.InvalidateLocalDataOnAppOpenUseCaseImpl
import com.example.inputofcalories.presentation.launch.InvalidateLocalDataViewModel
import com.example.inputofcalories.repo.launch.ClearDbRepo
import com.example.inputofcalories.repo.launch.ClearDbRepoImpl
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val launchermodule = module {

    single<ClearDbRepo> {
        ClearDbRepoImpl(get())
    }

    single<InvalidateLocalDataOnAppOpenUseCase> {
        InvalidateLocalDataOnAppOpenUseCaseImpl(get())
    }

    viewModel {
        InvalidateLocalDataViewModel(get())
    }
}