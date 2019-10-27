package com.example.inputofcalories.repo.regularflow

import com.example.inputofcalories.domain.regularflow.UpdateUsersDailyCaloriesUseCaseImpl
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Completable
import org.junit.Test

class UpdateDailyCaloriesTest {
    val providerRepo = mock<UpdateUsersDailyCaloriesRepo>()

    val updateDailyCaloriesUseCase = UpdateUsersDailyCaloriesUseCaseImpl(providerRepo)

    @Test
    fun `update succeed test`() {
        val uId = "4449fc18-0210-4f09-adfd-ed216369a173"

        val dailyCalories = "1000"

        whenever(providerRepo.update(uId, dailyCalories)).thenReturn(
            Completable.complete()
        )

        updateDailyCaloriesUseCase.update(uId, dailyCalories).test().assertResult()
    }

    @Test
    fun `update failed test`() {
        val uId = "4449fc18-0210-4f09-adfd-ed216369a173"

        val dailyCalories = ""

        val exception = Exception()

        whenever(providerRepo.update(uId, dailyCalories)).thenReturn(
            Completable.error(exception)
        )

        updateDailyCaloriesUseCase.update(uId, dailyCalories).test().assertError(exception)
    }
}