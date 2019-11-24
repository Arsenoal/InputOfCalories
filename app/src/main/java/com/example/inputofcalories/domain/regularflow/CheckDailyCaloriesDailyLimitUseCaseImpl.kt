package com.example.inputofcalories.domain.regularflow

import com.example.inputofcalories.repo.regularflow.DailyCaloriesProviderRepo
import com.example.inputofcalories.repo.regularflow.MealsProviderRepo
import com.example.inputofcalories.repo.user.GetUserRepo
import io.reactivex.Single
import java.util.*

class CheckDailyCaloriesDailyLimitUseCaseImpl(
    private val mealsProviderRepo: MealsProviderRepo,
    private val getUserRepo: GetUserRepo,
    private val dailyCaloriesProviderRepo: DailyCaloriesProviderRepo
): CheckDailyCaloriesDailyLimitUseCase {

    override suspend fun check(): Boolean {

        val user = getUserRepo.get()
        val meals = mealsProviderRepo.getMealsByUserId(user.id)

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

        val dailyLimit = dailyCaloriesProviderRepo.provide(user.id).toInt()

        return caloriesConsumed > dailyLimit
    }
}