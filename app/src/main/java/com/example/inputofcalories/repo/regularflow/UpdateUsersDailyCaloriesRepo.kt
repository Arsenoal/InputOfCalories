package com.example.inputofcalories.repo.regularflow

import io.reactivex.Completable

interface UpdateUsersDailyCaloriesRepo {
    fun update(userId: String, dailyCalories: String): Completable
}