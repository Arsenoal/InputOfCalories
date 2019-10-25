package com.example.inputofcalories.repo.regularflow

import com.example.inputofcalories.entity.presentation.regular.MealDeleteParams
import io.reactivex.Completable

interface DeleteMealRepo {
    fun delete(mealDeleteParams: MealDeleteParams): Completable
}