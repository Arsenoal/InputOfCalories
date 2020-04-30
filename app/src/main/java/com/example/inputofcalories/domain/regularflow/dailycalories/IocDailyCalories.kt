package com.example.inputofcalories.domain.regularflow.dailycalories

import com.example.inputofcalories.repo.regularflow.UserMealsRepo
import com.example.inputofcalories.repo.regularflow.dailycalories.DailyCaloriesRepo
import com.example.inputofcalories.repo.user.UserRepo
import java.util.*

class IocDailyCalories(
    private val userMealsRepo: UserMealsRepo,
    private val userRepo: UserRepo,
    private val dailyCaloriesRepo: DailyCaloriesRepo
): DailyCaloriesUseCase {

    override suspend fun isLimitExceeded(): Boolean {
        val user = userRepo.get()
        val meals = userMealsRepo.loadMeals(user.id)

        val dailyMeals = meals.filter { meal ->
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

            val date = meal.filterParams.date

            date.month.toInt() == month && date.year.toInt() == year && (dayOfMonth - date.dayOfMonth.toInt()) == 0
        }.toList()

        var caloriesConsumed = 0
        dailyMeals.forEach { meal -> caloriesConsumed += meal.params.calories.toInt() }

        val dailyLimit = with(dailyCaloriesRepo.getDailyCaloriesLimit(user.id)) {
            if(isNotBlank()) toInt()
            else 0
        }

        return if(dailyLimit == 0 ) false else caloriesConsumed > dailyLimit
    }

    override suspend fun updateDailyCaloriesLimit(userId: String, dailyCalories: String) = dailyCaloriesRepo.updateDailyCaloriesLimit(userId, dailyCalories)
}