package com.example.inputofcalories.domain.regularflow

import com.example.inputofcalories.entity.presentation.regular.Meal

interface EditMealUseCase {
    suspend fun edit(meal: Meal)
}