package com.example.inputofcalories.repo.regularflow

import com.example.inputofcalories.entity.Meal
import io.reactivex.Single

interface MealsProviderRepo {
    fun getMealsByUserId(uId: String): Single<List<Meal>>
}