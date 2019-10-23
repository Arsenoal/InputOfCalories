package com.example.inputofcalories.connector

import com.example.inputofcalories.domain.regularflow.*
import com.example.inputofcalories.domain.user.GetUserUseCase
import com.example.inputofcalories.domain.user.GetUserUseCaseImpl
import com.example.inputofcalories.presentation.regularflow.addmeal.AddMealViewModel
import com.example.inputofcalories.presentation.regularflow.home.MealsProviderViewModel
import com.example.inputofcalories.presentation.regularflow.editmeal.EditMealViewModel
import com.example.inputofcalories.repo.regularflow.*
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

    single<GetMealsFilteredUseCase> {
        GetMealsFilteredUseCaseImpl(get())
    }

    viewModel {
        MealsProviderViewModel(get(), get(), get())
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

    //edit meal
    single<EditMealRepo> {
        EditMealRepoImpl(get())
    }

    single<EditMealUseCase> {
        EditMealUseCaseImpl(get())
    }

    viewModel {
        EditMealViewModel(get())
    }

}