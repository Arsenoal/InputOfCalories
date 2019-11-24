package com.example.inputofcalories.domain.regularflow

import com.example.inputofcalories.entity.presentation.regular.MealDeleteParams

interface DeleteMealUseCase {
    suspend fun delete(mealDeleteParams: MealDeleteParams)
}