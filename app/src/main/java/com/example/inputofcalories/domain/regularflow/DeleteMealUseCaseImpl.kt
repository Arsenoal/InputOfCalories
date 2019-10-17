package com.example.inputofcalories.domain.regularflow

import com.example.inputofcalories.repo.regularflow.DeleteMealRepo
import io.reactivex.Completable
import java.util.*

class DeleteMealUseCaseImpl(
    private val deleteMealRepo: DeleteMealRepo
): DeleteMealRepo {
    override fun delete(mealId: UUID): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}