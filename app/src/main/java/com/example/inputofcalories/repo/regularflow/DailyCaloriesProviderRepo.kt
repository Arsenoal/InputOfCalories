package com.example.inputofcalories.repo.regularflow

interface DailyCaloriesProviderRepo {
    suspend fun provide(userId: String): String
}