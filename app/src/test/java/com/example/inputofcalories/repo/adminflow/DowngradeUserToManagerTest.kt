package com.example.inputofcalories.repo.adminflow

import com.example.inputofcalories.domain.adminflow.DowngradeUserToManagerUseCaseImpl
import com.example.inputofcalories.repo.MockDataProvider
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Completable
import org.junit.Test

class DowngradeUserToManagerTest {
    val downgradeUserToManagerRepo = mock<DowngradeUserToManagerRepo>()

    val downgradeUserToManagerUseCase = DowngradeUserToManagerUseCaseImpl(downgradeUserToManagerRepo)

    @Test
    fun `downgrade user succeed test`() {
        whenever(downgradeUserToManagerRepo.downgrade(MockDataProvider.user.id)).thenReturn(
            Completable.complete()
        )

        downgradeUserToManagerUseCase.downgrade(MockDataProvider.user.id).test().assertResult()
    }

    @Test
    fun `downgrade user fail test`() {
        val exception = Exception()

        whenever(downgradeUserToManagerRepo.downgrade(MockDataProvider.user.id)).thenReturn(
            Completable.error(exception)
        )

        downgradeUserToManagerUseCase.downgrade(MockDataProvider.user.id).test().assertError(exception)
    }
}