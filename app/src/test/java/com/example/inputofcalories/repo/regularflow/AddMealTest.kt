package com.example.inputofcalories.repo.regularflow

import com.example.inputofcalories.domain.regularflow.AddMealUseCaseImpl
import com.example.inputofcalories.repo.MockDataProvider
import com.example.inputofcalories.repo.user.GetUserRepo
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Test

class AddMealTest {
    val addMealRepo = mock<AddMealRepo>()

    val getUserRepo = mock<GetUserRepo>()

    val addMealUseCase = AddMealUseCaseImpl(getUserRepo, addMealRepo)

    @Test
    fun `add meal succeed test`() {
        whenever(addMealRepo.add(params = MockDataProvider.mealParams, filterParams = MockDataProvider.mealFilterParams)).thenReturn(
            Completable.complete()
        )

        whenever(getUserRepo.get()).thenReturn(Single.just(MockDataProvider.user))

        addMealUseCase.add(params = MockDataProvider.mealParams, filterParams = MockDataProvider.mealFilterParams).test().assertResult()
    }

    @Test
    fun `add meal repo exception test`() {
        val exception = Exception()

        whenever(addMealRepo.add(params = MockDataProvider.mealParams, filterParams = MockDataProvider.mealFilterParams)).thenReturn(
            Completable.error(exception)
        )

        whenever(getUserRepo.get()).thenReturn(Single.just(MockDataProvider.user))

        addMealUseCase.add(params = MockDataProvider.mealParams, filterParams = MockDataProvider.mealFilterParams).test().assertError(exception)
    }

    @Test
    fun `get user repo exception test`() {
        val exception = Exception()

        whenever(addMealRepo.add(params = MockDataProvider.mealParams, filterParams = MockDataProvider.mealFilterParams)).thenReturn(
            Completable.complete()
        )

        whenever(getUserRepo.get()).thenReturn(Single.error(exception))

        addMealUseCase.add(params = MockDataProvider.mealParams, filterParams = MockDataProvider.mealFilterParams).test().assertError(exception)
    }

}