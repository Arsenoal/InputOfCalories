package com.example.inputofcalories.repo.regularflow.dailycalories

interface DailyCaloriesRepo {
    suspend fun getDailyCaloriesLimit(userId: String): String

    suspend fun updateDailyCaloriesLimit(userId: String, dailyCaloriesLimit: String)
}