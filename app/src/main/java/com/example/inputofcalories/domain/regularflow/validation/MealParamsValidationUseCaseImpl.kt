package com.example.inputofcalories.domain.regularflow.validation

import com.example.inputofcalories.common.exception.MealException
import com.example.inputofcalories.entity.presentation.regular.MealParams
import io.reactivex.Completable

class MealParamsValidationUseCaseImpl: MealParamsValidationUseCase {
    override fun validate(mealParams: MealParams): Completable {
        return Completable.create { emitter ->
            mealParams.run {
                if(text.isNotBlank() && calories.isNotBlank() && weight.isNotBlank()) emitter.onComplete()
            }

            if (!emitter.isDisposed) emitter.onError(MealException())
        }
    }
}