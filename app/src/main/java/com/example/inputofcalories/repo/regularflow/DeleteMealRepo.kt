package com.example.inputofcalories.repo.regularflow

import com.example.inputofcalories.entity.presentation.regular.MealDeleteParams

interface DeleteMealRepo {
    suspend fun delete(mealDeleteParams: MealDeleteParams)
}