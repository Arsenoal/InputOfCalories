package com.example.inputofcalories.repo.regularflow

import com.example.inputofcalories.entity.presentation.regular.Meal
import com.example.inputofcalories.entity.presentation.regular.MealDeleteParams
import com.example.inputofcalories.entity.presentation.regular.MealFilterParams
import com.example.inputofcalories.entity.presentation.regular.MealParams
import java.util.*

interface UserMealsRepo {
    suspend fun addMeal(userId: String, params: MealParams, filterParams: MealFilterParams)

    suspend fun deleteMeal(mealDeleteParams: MealDeleteParams)

    suspend fun editMeal(meal: Meal)

    suspend fun loadMeals(uId: String): List<Meal>

    suspend fun loadMoreMeals(uId: String, date: Date): List<Meal>
}