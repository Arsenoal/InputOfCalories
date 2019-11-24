package com.example.inputofcalories.domain.regularflow

import com.example.inputofcalories.entity.presentation.regular.Meal
import com.example.inputofcalories.entity.presentation.regular.MealFilterParams

interface GetMealsFilteredUseCase {
    suspend fun get(uId: String, mealFilterParams: MealFilterParams): List<Meal>
}