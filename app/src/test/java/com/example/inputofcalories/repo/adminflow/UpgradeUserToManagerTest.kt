package com.example.inputofcalories.repo.adminflow

import com.example.inputofcalories.domain.adminflow.UpgradeUserToManagerUseCaseImpl
import com.example.inputofcalories.repo.MockDataProvider
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Completable
import org.junit.Test

class UpgradeUserToManagerTest {
    val upgradeUserToManagerRepo = mock<UpgradeUserToManagerRepo>()

    val upgradeUserToManagerUseCase = UpgradeUserToManagerUseCaseImpl(upgradeUserToManagerRepo)

    @Test
    fun `upgrade user to manager succeed test`() {
        whenever(upgradeUserToManagerRepo.upgrade(MockDataProvider.user.id)).thenReturn(Completable.complete())

        upgradeUserToManagerUseCase.upgrade(MockDataProvider.user.id).test().assertResult()
    }

    @Test
    fun `upgrade user to manager fail test`() {
        val exception = Exception()

        whenever(upgradeUserToManagerRepo.upgrade(MockDataProvider.user.id)).thenReturn(Completable.error(exception))

        upgradeUserToManagerUseCase.upgrade(MockDataProvider.user.id).test().assertError(exception)
    }
}