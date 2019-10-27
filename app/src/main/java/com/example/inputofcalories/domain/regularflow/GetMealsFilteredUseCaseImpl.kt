package com.example.inputofcalories.domain.regularflow

import com.example.inputofcalories.entity.presentation.regular.Meal
import com.example.inputofcalories.entity.presentation.regular.MealFilterParams
import com.example.inputofcalories.repo.regularflow.MealsProviderRepo
import io.reactivex.Single

class GetMealsFilteredUseCaseImpl(
    private val mealsProviderRepo: MealsProviderRepo
): GetMealsFilteredUseCase {
    override fun get(uId: String, mealFilterParams: MealFilterParams): Single<List<Meal>> {
        return mealsProviderRepo.getMealsByUserId(uId).map { meals ->
            meals.filter { it.filterParams == mealFilterParams }
        }
    }
}