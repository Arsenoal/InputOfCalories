package com.example.inputofcalories.domain.regularflow

import com.example.inputofcalories.entity.MealParams
import io.reactivex.Completable

interface EditMealUseCase {
    fun edit(mealId: String, params: MealParams): Completable
}