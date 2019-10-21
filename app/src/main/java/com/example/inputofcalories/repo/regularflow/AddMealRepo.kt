package com.example.inputofcalories.repo.regularflow

import com.example.inputofcalories.entity.MealParams
import io.reactivex.Completable

interface AddMealRepo {
    fun add(uId: String, mealParams: MealParams): Completable
}