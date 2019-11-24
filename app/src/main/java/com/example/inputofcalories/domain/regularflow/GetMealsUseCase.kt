package com.example.inputofcalories.domain.regularflow

import com.example.inputofcalories.entity.presentation.regular.Meal

interface GetMealsUseCase {
    suspend fun get(uId: String): List<Meal>
}