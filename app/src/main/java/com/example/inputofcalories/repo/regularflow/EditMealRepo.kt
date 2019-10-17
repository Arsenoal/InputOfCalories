package com.example.inputofcalories.repo.regularflow

import com.example.inputofcalories.entity.MealParams
import io.reactivex.Completable
import java.util.*

interface EditMealRepo {
    fun edit(mealId: UUID, params: MealParams): Completable
}