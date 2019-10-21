package com.example.inputofcalories.domain.regularflow

import com.example.inputofcalories.entity.MealParams
import com.example.inputofcalories.repo.regularflow.AddMealRepo
import com.example.inputofcalories.repo.user.GetUserRepo
import io.reactivex.Completable

class AddMealUseCaseImpl(
    private val getUserRepo: GetUserRepo,
    private val addMealRepo: AddMealRepo
): AddMealUseCase {
    override fun add(mealParams: MealParams): Completable {
        return getUserRepo.get().flatMapCompletable {
            addMealRepo.add(it.id, mealParams)
        }
    }
}