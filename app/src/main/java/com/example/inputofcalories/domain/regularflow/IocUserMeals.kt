package com.example.inputofcalories.domain.regularflow

import com.example.inputofcalories.entity.presentation.regular.Meal
import com.example.inputofcalories.entity.presentation.regular.MealDeleteParams
import com.example.inputofcalories.entity.presentation.regular.MealFilterParams
import com.example.inputofcalories.entity.presentation.regular.MealParams
import com.example.inputofcalories.repo.regularflow.UserMealsRepo
import com.example.inputofcalories.repo.user.UserRepo

class IocUserMeals(
    private val userRepo: UserRepo,
    private val userMealsRepo: UserMealsRepo
) : UserMealsUseCase {

    override suspend fun addMeal(params: MealParams, filterParams: MealFilterParams)
            = userMealsRepo.addMeal(userRepo.get().id, params, filterParams)

    override suspend fun deleteMeal(mealDeleteParams: MealDeleteParams)
            = userMealsRepo.deleteMeal(mealDeleteParams)

    override suspend fun editMeal(meal: Meal)
            = userMealsRepo.editMeal(meal)

    override suspend fun getMealsFiltered(uId: String, mealFilterParams: List<MealFilterParams>)
            = userMealsRepo.getMeals(uId).filter { mealFilterParams.contains(it.filterParams) }

    override suspend fun getMeals(uId: String)
            = userMealsRepo.getMeals(uId)
}