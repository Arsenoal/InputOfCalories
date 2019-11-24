package com.example.inputofcalories.domain.regularflow

interface UpdateUsersDailyCaloriesUseCase {
    suspend fun update(userId: String, dailyCalories: String)
}