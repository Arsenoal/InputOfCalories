package com.example.inputofcalories.domain.regularflow.validation

import com.example.inputofcalories.common.exception.ValidationException
import com.example.inputofcalories.entity.presentation.regular.MealParams

class MealParamsValidationUseCaseImpl: MealParamsValidationUseCase {
    override suspend fun validate(mealParams: MealParams) {
        mealParams.run {
            if(text.isBlank() || calories.isBlank() || weight.isBlank())
                throw ValidationException(message = "meal params are not valid")
        }
    }
}