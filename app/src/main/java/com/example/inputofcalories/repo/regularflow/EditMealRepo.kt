package com.example.inputofcalories.repo.regularflow

import com.example.inputofcalories.entity.MealParams
import io.reactivex.Completable

interface EditMealRepo {
    fun edit(mealId: String, params: MealParams): Completable
}