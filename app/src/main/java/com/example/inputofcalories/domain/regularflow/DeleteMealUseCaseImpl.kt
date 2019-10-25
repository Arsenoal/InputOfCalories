package com.example.inputofcalories.domain.regularflow

import com.example.inputofcalories.entity.presentation.regular.MealDeleteParams
import com.example.inputofcalories.repo.regularflow.DeleteMealRepo

class DeleteMealUseCaseImpl(
    private val deleteMealRepo: DeleteMealRepo
): DeleteMealUseCase {
    override fun delete(mealDeleteParams: MealDeleteParams) = deleteMealRepo.delete(mealDeleteParams)
}