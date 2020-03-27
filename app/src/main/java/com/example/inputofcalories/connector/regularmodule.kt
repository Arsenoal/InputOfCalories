package com.example.inputofcalories.connector

import com.example.inputofcalories.domain.regularflow.IocUserMeals
import com.example.inputofcalories.domain.regularflow.UserMealsUseCase
import com.example.inputofcalories.domain.regularflow.dailycalories.DailyCaloriesUseCase
import com.example.inputofcalories.domain.regularflow.dailycalories.IocDailyCalories
import com.example.inputofcalories.domain.regularflow.validation.MealParamsValidationUseCase
import com.example.inputofcalories.domain.regularflow.validation.IocMealParamsValidation
import com.example.inputofcalories.domain.user.GetUserUseCase
import com.example.inputofcalories.domain.user.GetUserUseCaseImpl
import com.example.inputofcalories.presentation.regularflow.addmeal.AddMealViewModel
import com.example.inputofcalories.presentation.regularflow.home.viewmodel.MealsProviderViewModel
import com.example.inputofcalories.presentation.regularflow.editmeal.EditMealViewModel
import com.example.inputofcalories.presentation.regularflow.home.viewmodel.CheckDailyLimitViewModel
import com.example.inputofcalories.presentation.regularflow.home.viewmodel.DeleteMealViewModel
import com.example.inputofcalories.presentation.regularflow.home.viewmodel.UpdateDailyCaloriesViewModel
import com.example.inputofcalories.repo.regularflow.*
import com.example.inputofcalories.repo.regularflow.dailycalories.DailyCaloriesFirestore
import com.example.inputofcalories.repo.regularflow.dailycalories.DailyCaloriesRepo
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

    single<UserMealsRepo> {
        UserMealsFirestore(get(), get())
    }

    single<UserMealsUseCase> {
        IocUserMeals(get(), get())
    }

    viewModel { params ->
        val userId: String = params[0]
        MealsProviderViewModel(
            userId,
            get()
        )
    }

    single<MealParamsValidationUseCase> {
        IocMealParamsValidation()
    }

    viewModel {
        AddMealViewModel(get(), get())
    }

    viewModel {
        EditMealViewModel(get())
    }

    viewModel { params ->
        val userId: String = params[0]
        DeleteMealViewModel(
            userId,
            get()
        )
    }

    single<DailyCaloriesRepo> {
        DailyCaloriesFirestore(get())
    }

    single<DailyCaloriesUseCase> {
        IocDailyCalories(get(), get(), get())
    }

    viewModel { params ->
        val userId: String = params[0]
        UpdateDailyCaloriesViewModel(
            userId,
            get()
        )
    }

    viewModel {
        CheckDailyLimitViewModel(
            get()
        )
    }

}