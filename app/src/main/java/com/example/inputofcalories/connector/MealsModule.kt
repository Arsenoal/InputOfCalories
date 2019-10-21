package com.example.inputofcalories.connector

import com.example.inputofcalories.domain.regularflow.AddMealUseCase
import com.example.inputofcalories.domain.regularflow.AddMealUseCaseImpl
import com.example.inputofcalories.domain.regularflow.GetMealsUseCase
import com.example.inputofcalories.domain.regularflow.GetMealsUseCaseImpl
import com.example.inputofcalories.domain.user.GetUserUseCase
import com.example.inputofcalories.domain.user.GetUserUseCaseImpl
import com.example.inputofcalories.presentation.regularflow.AddMealViewModel
import com.example.inputofcalories.presentation.regularflow.MealsProviderViewModel
import com.example.inputofcalories.repo.regularflow.AddMealRepo
import com.example.inputofcalories.repo.regularflow.AddMealRepoImpl
import com.example.inputofcalories.repo.regularflow.MealsProviderRepo
import com.example.inputofcalories.repo.regularflow.MealsProviderRepoImpl
import com.example.inputofcalories.repo.user.GetUserFromLocalRepoImpl
import com.example.inputofcalories.repo.user.GetUserRepo
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mealsmodule = module {

    single<GetUserRepo> {
        GetUserFromLocalRepoImpl(get())
    }

    single<GetUserUseCase> {
        GetUserUseCaseImpl(get())
    }

    //get meals
    single<MealsProviderRepo> {
        MealsProviderRepoImpl(get())
    }

    single<GetMealsUseCase> {
        GetMealsUseCaseImpl(get())
    }

    viewModel {
        MealsProviderViewModel(get(), get())
    }

    //add meal
    single<AddMealRepo> {
        AddMealRepoImpl(get(), get())
    }

    single<AddMealUseCase> {
        AddMealUseCaseImpl(get(), get())
    }

    viewModel {
        AddMealViewModel(get())
    }

}