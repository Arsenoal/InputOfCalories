package com.example.inputofcalories.domain.regularflow

import com.example.inputofcalories.entity.presentation.regular.Meal
import com.example.inputofcalories.entity.presentation.regular.MealFilterParams
import io.reactivex.Single

interface GetMealsFilteredUseCase {
    fun get(uId: String, mealFilterParams: MealFilterParams): Single<List<Meal>>
}