package com.example.inputofcalories.domain.regularflow

import com.example.inputofcalories.entity.presentation.regular.MealFilterParams
import com.example.inputofcalories.entity.presentation.regular.MealParams
import com.example.inputofcalories.repo.regularflow.AddMealRepo
import com.example.inputofcalories.repo.user.GetUserRepo
import io.reactivex.Completable

class AddMealUseCaseImpl(
    private val getUserRepo: GetUserRepo,
    private val addMealRepo: AddMealRepo
): AddMealUseCase {
    override fun add(params: MealParams, filterParams: MealFilterParams): Completable {
        return getUserRepo.get().flatMapCompletable {
            addMealRepo.add(params, filterParams)
        }
    }
}