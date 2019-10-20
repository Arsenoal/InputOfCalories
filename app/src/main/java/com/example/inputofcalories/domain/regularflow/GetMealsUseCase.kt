package com.example.inputofcalories.domain.regularflow

import com.example.inputofcalories.entity.Meal
import io.reactivex.Single

interface GetMealsUseCase {
    fun get(uId: String): Single<List<Meal>>
}