package com.example.inputofcalories.repo.regularflow

import com.example.inputofcalories.entity.presentation.regular.Meal

interface EditMealRepo {
    suspend fun edit(meal: Meal)
}