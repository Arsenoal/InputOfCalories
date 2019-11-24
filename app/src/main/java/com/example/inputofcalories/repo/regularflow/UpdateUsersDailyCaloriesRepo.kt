package com.example.inputofcalories.repo.regularflow

interface UpdateUsersDailyCaloriesRepo {
    suspend fun update(userId: String, dailyCalories: String)
}