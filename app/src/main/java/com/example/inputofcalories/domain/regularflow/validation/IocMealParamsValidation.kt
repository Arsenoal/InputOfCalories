package com.example.inputofcalories.domain.regularflow.validation

import com.example.inputofcalories.entity.presentation.regular.MealParams

class IocMealParamsValidation: MealParamsValidationUseCase {
    override suspend fun isValid(mealParams: MealParams) = with(mealParams) {
        text.isNotBlank() && calories.isNotBlank() && weight.isNotBlank()
    }
}