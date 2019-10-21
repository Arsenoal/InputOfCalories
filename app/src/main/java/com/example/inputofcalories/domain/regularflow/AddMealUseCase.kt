package com.example.inputofcalories.domain.regularflow

import com.example.inputofcalories.entity.MealParams
import io.reactivex.Completable

interface AddMealUseCase {
    fun add(mealParams: MealParams): Completable
}