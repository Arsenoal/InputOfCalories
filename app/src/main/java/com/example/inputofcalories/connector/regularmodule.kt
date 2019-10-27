package com.example.inputofcalories.connector

import com.example.inputofcalories.domain.regularflow.*
import com.example.inputofcalories.domain.user.GetUserUseCase
import com.example.inputofcalories.domain.user.GetUserUseCaseImpl
import com.example.inputofcalories.presentation.regularflow.addmeal.AddMealViewModel
import com.example.inputofcalories.presentation.regularflow.home.MealsProviderViewModel
import com.example.inputofcalories.presentation.regularflow.editmeal.EditMealViewModel
import com.example.inputofcalories.presentation.regularflow.home.CheckDailyLimitViewModel
import com.example.inputofcalories.presentation.regularflow.home.DeleteMealViewModel
import com.example.inputofcalories.presentation.regularflow.home.UpdateDailyCaloriesViewModel
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

    viewModel { params ->
        val userId: String = params[0]
        MealsProviderViewModel(userId, get(), get())
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

    //delete meal
    single<DeleteMealRepo> {
        DeleteMealRepoImpl(get())
    }

    single<DeleteMealUseCase> {
        DeleteMealUseCaseImpl(get())
    }

    viewModel { params ->
        val userId: String = params[0]
        DeleteMealViewModel(userId, get())
    }

    //update daily calories

    single<UpdateUsersDailyCaloriesRepo> {
        UpdateUsersDailyCaloriesRepoImpl(get())
    }

    single<UpdateUsersDailyCaloriesUseCase> {
        UpdateUsersDailyCaloriesUseCaseImpl(get())
    }

    viewModel { params ->
        val userId: String = params[0]
        UpdateDailyCaloriesViewModel(userId, get())
    }

    //check daily calories limit

    single<DailyCaloriesProviderRepo> {
        DailyCaloriesProviderRepoImpl(get())
    }

    single<CheckDailyCaloriesDailyLimitUseCase> {
        CheckDailyCaloriesDailyLimitUseCaseImpl(get(), get(), get())
    }

    viewModel {
        CheckDailyLimitViewModel(get())
    }

}