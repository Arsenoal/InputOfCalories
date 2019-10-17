package com.example.inputofcalories.domain.regularflow

import com.example.inputofcalories.entity.Meal
import com.example.inputofcalories.repo.regularflow.MealsProviderRepo
import io.reactivex.Single

class GetMealsUseCaseImpl(
    private val mealsProviderRepo: MealsProviderRepo
): GetMealsUseCase {
    override fun get(): Single<List<Meal>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}