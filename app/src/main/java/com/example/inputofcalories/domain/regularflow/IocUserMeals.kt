package com.example.inputofcalories.domain.regularflow

import com.example.inputofcalories.entity.presentation.regular.Meal
import com.example.inputofcalories.entity.presentation.regular.MealDeleteParams
import com.example.inputofcalories.entity.presentation.regular.MealFilterParams
import com.example.inputofcalories.entity.presentation.regular.MealParams
import com.example.inputofcalories.repo.regularflow.UserMealsRepo
import com.example.inputofcalories.repo.user.GetUserRepo

class IocUserMeals(
    private val getUserRepo: GetUserRepo,
    private val userMealsRepo: UserMealsRepo
) : UserMealsUseCase {

    override suspend fun addMeal(params: MealParams, filterParams: MealFilterParams) {
        userMealsRepo.addMeal(getUserRepo.get().id, params, filterParams)
    }

    override suspend fun deleteMeal(mealDeleteParams: MealDeleteParams) = userMealsRepo.deleteMeal(mealDeleteParams)

    override suspend fun editMeal(meal: Meal) = userMealsRepo.editMeal(meal)

    override suspend fun getMealsFiltered(uId: String, mealFilterParams: MealFilterParams)
            = userMealsRepo.getMeals(uId).filter { it.filterParams == mealFilterParams }

    override suspend fun getMeals(uId: String) = userMealsRepo.getMeals(uId)
}