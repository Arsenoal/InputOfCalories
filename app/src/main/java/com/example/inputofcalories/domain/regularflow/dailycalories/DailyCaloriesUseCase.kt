package com.example.inputofcalories.domain.regularflow.dailycalories

import com.example.inputofcalories.entity.presentation.regular.Meal

interface DailyCaloriesUseCase {
    suspend fun isLimitExceeded(meals: List<Meal>): Boolean

    suspend fun updateDailyCaloriesLimit(dailyCalories: String)
}