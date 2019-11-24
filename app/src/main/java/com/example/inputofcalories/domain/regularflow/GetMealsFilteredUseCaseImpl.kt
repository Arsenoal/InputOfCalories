package com.example.inputofcalories.domain.regularflow

import com.example.inputofcalories.entity.presentation.regular.Meal
import com.example.inputofcalories.entity.presentation.regular.MealFilterParams
import com.example.inputofcalories.repo.regularflow.MealsProviderRepo
import io.reactivex.Single

class GetMealsFilteredUseCaseImpl(
    private val mealsProviderRepo: MealsProviderRepo
): GetMealsFilteredUseCase {
    override suspend fun get(uId: String, mealFilterParams: MealFilterParams): List<Meal> {
        return mealsProviderRepo.getMealsByUserId(uId).filter { it.filterParams == mealFilterParams }
    }
}