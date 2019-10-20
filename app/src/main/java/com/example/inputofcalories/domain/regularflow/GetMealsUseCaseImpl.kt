package com.example.inputofcalories.domain.regularflow

import com.example.inputofcalories.repo.regularflow.MealsProviderRepo

class GetMealsUseCaseImpl(
    private val mealsProviderRepo: MealsProviderRepo
): GetMealsUseCase {
    override fun get(uId: String) = mealsProviderRepo.getMealsByUserId(uId)
}