package com.example.inputofcalories.repo.regularflow

import com.example.inputofcalories.domain.regularflow.DeleteMealUseCaseImpl
import com.example.inputofcalories.repo.MockDataProvider
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Completable
import org.junit.Test

class DeleteMealTest {
    val deleteMealRepo = mock<DeleteMealRepo>()

    val deleteMealUseCase = DeleteMealUseCaseImpl(deleteMealRepo)

    @Test
    fun `delete succeed test`() {
        whenever(deleteMealRepo.delete(MockDataProvider.mealDeleteParams)).thenReturn(
            Completable.complete()
        )

        deleteMealUseCase.delete(MockDataProvider.mealDeleteParams).test().assertResult()
    }

    @Test
    fun `delete failure test`() {
        val exception = Exception()

        whenever(deleteMealRepo.delete(MockDataProvider.mealDeleteParams)).thenReturn(
            Completable.error(exception)
        )

        deleteMealUseCase.delete(MockDataProvider.mealDeleteParams).test().assertError(exception)
    }
}