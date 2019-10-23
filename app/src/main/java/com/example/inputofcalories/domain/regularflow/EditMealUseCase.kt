package com.example.inputofcalories.domain.regularflow

import com.example.inputofcalories.entity.presentation.regular.Meal
import io.reactivex.Completable

interface EditMealUseCase {
    fun edit(meal: Meal): Completable
}