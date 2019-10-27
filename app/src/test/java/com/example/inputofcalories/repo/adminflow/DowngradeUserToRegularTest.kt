package com.example.inputofcalories.repo.adminflow

import com.example.inputofcalories.domain.adminflow.DowngradeUserToRegularUseCaseImpl
import com.example.inputofcalories.repo.MockDataProvider
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Completable
import org.junit.Test

class DowngradeUserToRegularTest {
    val downgradeUserToRegularRepo = mock<DowngradeUserToRegularRepo>()

    val downgradeUserToRegularUseCase = DowngradeUserToRegularUseCaseImpl(downgradeUserToRegularRepo)

    @Test
    fun `downgrade user succeed test`() {
        whenever(downgradeUserToRegularRepo.downgrade(MockDataProvider.user.id)).thenReturn(
            Completable.complete()
        )

        downgradeUserToRegularUseCase.downgrade(MockDataProvider.user.id).test().assertResult()
    }

    @Test
    fun `downgrade user fail test`() {
        val exception = Exception()

        whenever(downgradeUserToRegularRepo.downgrade(MockDataProvider.user.id)).thenReturn(
            Completable.error(exception)
        )

        downgradeUserToRegularUseCase.downgrade(MockDataProvider.user.id).test().assertError(exception)
    }
}