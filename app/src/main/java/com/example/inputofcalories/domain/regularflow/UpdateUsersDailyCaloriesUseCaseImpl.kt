package com.example.inputofcalories.domain.regularflow

import com.example.inputofcalories.repo.regularflow.UpdateUsersDailyCaloriesRepo

class UpdateUsersDailyCaloriesUseCaseImpl(
    private val updateUsersDailyCaloriesRepo: UpdateUsersDailyCaloriesRepo
): UpdateUsersDailyCaloriesUseCase {
    override suspend fun update(userId: String, dailyCalories: String) = updateUsersDailyCaloriesRepo.update(userId, dailyCalories)
}