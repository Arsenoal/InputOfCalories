package com.example.inputofcalories.domain.regularflow

import com.example.inputofcalories.entity.presentation.regular.Meal
import com.example.inputofcalories.repo.regularflow.EditMealRepo

class EditMealUseCaseImpl(
    private val editMealRepo: EditMealRepo
): EditMealUseCase {
    override fun edit(meal: Meal) = editMealRepo.edit(meal)
}