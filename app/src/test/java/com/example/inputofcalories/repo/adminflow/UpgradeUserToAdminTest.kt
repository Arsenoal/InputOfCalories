package com.example.inputofcalories.repo.adminflow

import com.example.inputofcalories.domain.adminflow.UpgradeUserToAdminUseCaseImpl
import com.example.inputofcalories.repo.MockDataProvider
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Completable
import org.junit.Test
import java.lang.Exception

class UpgradeUserToAdminTest {
    val upgradeUserToAdminRepo = mock<UpgradeUserToAdminRepo>()

    val upgradeUserToAdminUseCase = UpgradeUserToAdminUseCaseImpl(upgradeUserToAdminRepo)

    @Test
    fun `upgrade user to admin success test`() {
        whenever(upgradeUserToAdminRepo.upgrade(MockDataProvider.user.id)).thenReturn(Completable.complete())

        upgradeUserToAdminUseCase.upgrade(MockDataProvider.user.id).test().assertResult()
    }

    @Test
    fun `upgrade user to admin fail test`() {
        val exception = Exception()

        whenever(upgradeUserToAdminRepo.upgrade(MockDataProvider.user.id)).thenReturn(Completable.error(exception))

        upgradeUserToAdminUseCase.upgrade(MockDataProvider.user.id).test().assertError(exception)
    }
}