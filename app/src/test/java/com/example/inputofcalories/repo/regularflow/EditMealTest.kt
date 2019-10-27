package com.example.inputofcalories.repo.regularflow

import com.example.inputofcalories.domain.regularflow.EditMealUseCaseImpl
import com.example.inputofcalories.repo.MockDataProvider
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Completable
import org.junit.Test

class EditMealTest {
    val editMealRepo = mock<EditMealRepo>()

    val editMealUseCase = EditMealUseCaseImpl(editMealRepo)

    @Test
    fun `edit succeed test`() {
        whenever(editMealRepo.edit(MockDataProvider.mealToEdit)).thenReturn(
            Completable.complete()
        )

        editMealUseCase.edit(MockDataProvider.mealToEdit).test().assertResult()
    }

    @Test
    fun `edit failed test`() {
        val exception = Exception()

        whenever(editMealRepo.edit(MockDataProvider.mealToEdit)).thenReturn(
            Completable.error(exception)
        )

        editMealUseCase.edit(MockDataProvider.mealToEdit).test().assertError(exception)
    }
}