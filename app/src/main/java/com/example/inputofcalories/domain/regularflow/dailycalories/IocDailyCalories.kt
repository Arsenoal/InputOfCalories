package com.example.inputofcalories.domain.regularflow.dailycalories

import com.example.inputofcalories.entity.presentation.regular.Meal
import com.example.inputofcalories.repo.regularflow.dailycalories.DailyCaloriesRepo
import com.example.inputofcalories.repo.user.UserRepo

class IocDailyCalories(
    private val userRepo: UserRepo,
    private val dailyCaloriesRepo: DailyCaloriesRepo
): DailyCaloriesUseCase {

    override suspend fun isLimitExceeded(meals: List<Meal>): Boolean {
        var caloriesConsumed = 0
        meals.forEach { meal -> caloriesConsumed += meal.params.calories.toInt() }
        val limit = dailyCaloriesRepo.getDailyCaloriesLimit(userRepo.get().id)

        val dailyLimit = if(limit.isNotBlank()) limit.toInt() else 0

        return if(dailyLimit == 0 ) false else caloriesConsumed > dailyLimit
    }

    override suspend fun updateDailyCaloriesLimit(dailyCalories: String)
            = dailyCaloriesRepo.updateDailyCaloriesLimit(userRepo.get().id, dailyCalories)
}