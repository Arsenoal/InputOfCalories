package com.example.inputofcalories.domain.regularflow.dailycalories

interface DailyCaloriesUseCase {
    suspend fun isLimitExceeded(): Boolean

    suspend fun updateDailyCaloriesLimit(userId: String, dailyCalories: String)
}