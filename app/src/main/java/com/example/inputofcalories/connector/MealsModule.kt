package com.example.inputofcalories.connector

import com.example.inputofcalories.domain.regularflow.GetMealsUseCase
import com.example.inputofcalories.domain.regularflow.GetMealsUseCaseImpl
import com.example.inputofcalories.domain.user.GetUserFromLocalUseCase
import com.example.inputofcalories.domain.user.GetUserFromLocalUseCaseImpl
import com.example.inputofcalories.presentation.regularflow.MealsProviderViewModel
import com.example.inputofcalories.repo.regularflow.MealsProviderRepo
import com.example.inputofcalories.repo.regularflow.MealsProviderRepoImpl
import com.example.inputofcalories.repo.user.GetUserFromLocalRepo
import com.example.inputofcalories.repo.user.GetUserFromLocalRepoImpl
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mealsmodule = module {
    single<MealsProviderRepo> {
        MealsProviderRepoImpl(get())
    }

    single<GetUserFromLocalRepo> {
        GetUserFromLocalRepoImpl(get())
    }

    single<GetMealsUseCase> {
        GetMealsUseCaseImpl(get())
    }

    single<GetUserFromLocalUseCase> {
        GetUserFromLocalUseCaseImpl(get())
    }

    viewModel {
        MealsProviderViewModel(get(), get())
    }
}