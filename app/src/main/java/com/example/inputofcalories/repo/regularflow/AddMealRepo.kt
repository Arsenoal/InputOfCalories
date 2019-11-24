package com.example.inputofcalories.repo.regularflow

import com.example.inputofcalories.entity.presentation.regular.MealFilterParams
import com.example.inputofcalories.entity.presentation.regular.MealParams
import io.reactivex.Completable

interface AddMealRepo {
    suspend fun add(userId: String, params: MealParams, filterParams: MealFilterParams)
}