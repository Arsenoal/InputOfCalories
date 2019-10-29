package com.example.inputofcalories.domain.regularflow.validation

import com.example.inputofcalories.entity.presentation.regular.MealParams
import io.reactivex.Completable

interface MealParamsValidationUseCase {
    fun validate(mealParams: MealParams): Completable
}