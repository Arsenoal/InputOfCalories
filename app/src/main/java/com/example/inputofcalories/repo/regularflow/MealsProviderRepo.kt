package com.example.inputofcalories.repo.regularflow

import com.example.inputofcalories.entity.presentation.regular.Meal

interface MealsProviderRepo {
    suspend fun getMealsByUserId(uId: String): List<Meal>
}