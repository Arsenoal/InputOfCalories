package com.example.inputofcalories.domain.regularflow

import com.example.inputofcalories.entity.MealParams
import io.reactivex.Completable
import java.util.*

interface EditMealUseCase {
    fun edit(mealId: UUID, params: MealParams): Completable
}