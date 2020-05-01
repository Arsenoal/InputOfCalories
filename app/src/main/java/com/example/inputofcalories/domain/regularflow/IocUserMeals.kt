package com.example.inputofcalories.domain.regularflow

import com.example.inputofcalories.entity.presentation.regular.Meal
import com.example.inputofcalories.entity.presentation.regular.MealDeleteParams
import com.example.inputofcalories.entity.presentation.regular.MealFilterParams
import com.example.inputofcalories.entity.presentation.regular.MealParams
import com.example.inputofcalories.repo.regularflow.UserMealsRepo
import com.example.inputofcalories.repo.user.UserRepo
import java.util.*

class IocUserMeals(
    private val userRepo: UserRepo,
    private val userMealsRepo: UserMealsRepo
) : UserMealsUseCase {

    override suspend fun addMeal(params: MealParams, filterParams: MealFilterParams)
            = with(userMealsRepo) { addMeal(userRepo.get().id, params, filterParams) }

    override suspend fun deleteMeal(mealDeleteParams: MealDeleteParams)
            = with(userMealsRepo) { deleteMeal(userRepo.get().id, mealDeleteParams) }

    override suspend fun editMeal(meal: Meal)
            = with(userMealsRepo) { editMeal(userRepo.get().id, meal) }

    override suspend fun getMealsFiltered(mealFilterParams: List<MealFilterParams>)
            = with(userMealsRepo) { loadMeals(userRepo.get().id).filter { meal -> mealFilterParams.contains(meal.filterParams) } }

    override suspend fun loadMeals()
            = with(userMealsRepo) { loadMeals(userRepo.get().id) }

    override suspend fun loadMoreMeals(date: Date)
            = with(userMealsRepo) { loadMoreMeals(userRepo.get().id, date) }
}