package com.example.inputofcalories.repo.regularflow

import com.example.inputofcalories.entity.presentation.regular.Meal
import com.example.inputofcalories.entity.presentation.regular.MealFilterParams
import io.reactivex.Single

interface MealsProviderRepo {
    fun getMealsByUserId(uId: String): Single<List<Meal>>

    fun getMealsByUserIdAndFilterParams(uId: String, mealFilterParams: MealFilterParams): Single<List<Meal>>
}