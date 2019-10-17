package com.example.inputofcalories.repo.regularflow

import io.reactivex.Completable
import java.util.*

interface DeleteMealRepo {
    fun delete(mealId: UUID): Completable
}