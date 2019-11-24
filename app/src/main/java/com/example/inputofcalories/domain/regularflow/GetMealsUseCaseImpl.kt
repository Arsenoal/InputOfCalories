package com.example.inputofcalories.domain.regularflow

import com.example.inputofcalories.repo.regularflow.MealsProviderRepo

class GetMealsUseCaseImpl(
    private val mealsProviderRepo: MealsProviderRepo
): GetMealsUseCase {
    override suspend fun get(uId: String) = mealsProviderRepo.getMealsByUserId(uId)
}