package com.example.inputofcalories.repo.regularflow

import com.example.inputofcalories.entity.presentation.regular.Meal
import io.reactivex.Completable

interface EditMealRepo {
    fun edit(meal: Meal): Completable
}