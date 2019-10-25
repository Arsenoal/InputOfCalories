package com.example.inputofcalories.domain.regularflow

import com.example.inputofcalories.entity.presentation.regular.MealDeleteParams
import io.reactivex.Completable

interface DeleteMealUseCase {
    fun delete(mealDeleteParams: MealDeleteParams): Completable
}