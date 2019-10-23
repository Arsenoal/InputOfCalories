package com.example.inputofcalories.domain.regularflow

import com.example.inputofcalories.entity.presentation.regular.MealFilterParams
import com.example.inputofcalories.repo.regularflow.MealsProviderRepo

class GetMealsFilteredUseCaseImpl(
    private val mealsProviderRepo: MealsProviderRepo
): GetMealsFilteredUseCase {
    override fun get(uId: String, mealFilterParams: MealFilterParams) = mealsProviderRepo.getMealsByUserIdAndFilterParams(uId, mealFilterParams)
}