package com.example.inputofcalories.connector

import com.example.inputofcalories.domain.regularflow.IocUserMeals
import com.example.inputofcalories.domain.regularflow.UserMealsUseCase
import com.example.inputofcalories.domain.regularflow.dailycalories.DailyCaloriesUseCase
import com.example.inputofcalories.domain.regularflow.dailycalories.IocDailyCalories
import com.example.inputofcalories.domain.regularflow.validation.MealParamsValidationUseCase
import com.example.inputofcalories.domain.regularflow.validation.IocMealParamsValidation
import com.example.inputofcalories.presentation.regularflow.addmeal.AddMealViewModel
import com.example.inputofcalories.presentation.regularflow.editmeal.EditMealViewModel
import com.example.inputofcalories.presentation.regularflow.home.viewmodel.*
import com.example.inputofcalories.repo.regularflow.*
import com.example.inputofcalories.repo.regularflow.dailycalories.DailyCaloriesFirestore
import com.example.inputofcalories.repo.regularflow.dailycalories.DailyCaloriesRepo
import com.example.inputofcalories.repo.service.uuidgenerator.IOCUUIDGenerator
import com.example.inputofcalories.repo.service.uuidgenerator.UUIDGeneratorService
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mealsmodule = module {

    single<UUIDGeneratorService> { IOCUUIDGenerator() }

    single<UserMealsRepo> { UserMealsFirestore(get(), get()) }

    single<UserMealsUseCase> { IocUserMeals(get(), get()) }

    single<MealParamsValidationUseCase> { IocMealParamsValidation() }

    viewModel { AddMealViewModel(get(), get()) }

    viewModel { EditMealViewModel(get()) }

    single<DailyCaloriesRepo> { DailyCaloriesFirestore(get()) }

    single<DailyCaloriesUseCase> { IocDailyCalories(get(), get(), get()) }

    viewModel { DailyCaloriesViewModel(get(), get()) }

    viewModel { MealsViewModel(get(), get()) }

}