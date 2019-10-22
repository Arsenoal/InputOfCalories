package com.example.inputofcalories.domain.regularflow

import com.example.inputofcalories.entity.MealParams
import com.example.inputofcalories.repo.regularflow.EditMealRepo

class EditMealUseCaseImpl(
    private val editMealRepo: EditMealRepo
): EditMealUseCase {
    override fun edit(mealId: String, params: MealParams) = editMealRepo.edit(mealId, params)
}