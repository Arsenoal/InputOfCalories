package com.example.inputofcalories.domain.regularflow

import com.example.inputofcalories.entity.presentation.regular.MealFilterParams
import com.example.inputofcalories.entity.presentation.regular.MealParams
import io.reactivex.Completable

interface AddMealUseCase {
    suspend fun add(params: MealParams, filterParams: MealFilterParams)
}