package com.example.inputofcalories.connector

import com.example.inputofcalories.domain.regularflow.*
import com.example.inputofcalories.domain.regularflow.validation.MealParamsValidationUseCase
import com.example.inputofcalories.domain.regularflow.validation.MealParamsValidationUseCaseImpl
import com.example.inputofcalories.domain.user.GetUserUseCase
import com.example.inputofcalories.domain.user.GetUserUseCaseImpl
import com.example.inputofcalories.presentation.regularflow.addmeal.AddMealViewModel
import com.example.inputofcalories.presentation.regularflow.home.viewmodel.MealsProviderViewModel
import com.example.inputofcalories.presentation.regularflow.editmeal.EditMealViewModel
import com.example.inputofcalories.presentation.regularflow.home.viewmodel.CheckDailyLimitViewModel
import com.example.inputofcalories.presentation.regularflow.home.viewmodel.DeleteMealViewModel
import com.example.inputofcalories.presentation.regularflow.home.viewmodel.UpdateDailyCaloriesViewModel
import com.example.inputofcalories.repo.regularflow.*
import com.example.inputofcalories.repo.user.GetUserFromRoom
import com.example.inputofcalories.repo.user.GetUserRepo
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mealsmodule = module {

    single<GetUserRepo> {
        GetUserFromRoom(get())
    }

    single<GetUserUseCase> {
        GetUserUseCaseImpl(get())
    }

    //get meals
    single<MealsProviderRepo> {
        MealsProviderFirestore(get())
    }

    single<GetMealsUseCase> {
        GetMealsUseCaseImpl(get())
    }

    single<GetMealsFilteredUseCase> {
        GetMealsFilteredUseCaseImpl(get())
    }

    viewModel { params ->
        val userId: String = params[0]
        MealsProviderViewModel(
            userId,
            get(),
            get()
        )
    }

    //add meal
    single<AddMealRepo> {
        AddMealFirestore(get(), get())
    }

    single<AddMealUseCase> {
        AddMealUseCaseImpl(get(), get())
    }

    single<MealParamsValidationUseCase> {
        MealParamsValidationUseCaseImpl()
    }

    viewModel {
        AddMealViewModel(get(), get())
    }

    //edit meal
    single<EditMealRepo> {
        EditMealFirestore(get())
    }

    single<EditMealUseCase> {
        EditMealUseCaseImpl(get())
    }

    viewModel {
        EditMealViewModel(get())
    }

    //delete meal
    single<DeleteMealRepo> {
        DeleteMealFirestore(get())
    }

    single<DeleteMealUseCase> {
        DeleteMealUseCaseImpl(get())
    }

    viewModel { params ->
        val userId: String = params[0]
        DeleteMealViewModel(
            userId,
            get()
        )
    }

    //update daily calories

    single<UpdateUsersDailyCaloriesRepo> {
        UpdateUsersDailyCaloriesFirestore(get())
    }

    single<UpdateUsersDailyCaloriesUseCase> {
        UpdateUsersDailyCaloriesUseCaseImpl(get())
    }

    viewModel { params ->
        val userId: String = params[0]
        UpdateDailyCaloriesViewModel(
            userId,
            get()
        )
    }

    //check daily calories limit

    single<DailyCaloriesProviderRepo> {
        DailyCaloriesProviderFirestore(get())
    }

    single<CheckDailyCaloriesDailyLimitUseCase> {
        CheckDailyCaloriesDailyLimitUseCaseImpl(get(), get(), get())
    }

    viewModel {
        CheckDailyLimitViewModel(
            get()
        )
    }

}