package com.example.inputofcalories.domain.regularflow

import io.reactivex.Completable

interface UpdateUsersDailyCaloriesUseCase {
    fun update(userId: String, dailyCalories: String): Completable
}