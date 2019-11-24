package com.example.inputofcalories.domain.regularflow

interface CheckDailyCaloriesDailyLimitUseCase {
    suspend fun check(): Boolean
}