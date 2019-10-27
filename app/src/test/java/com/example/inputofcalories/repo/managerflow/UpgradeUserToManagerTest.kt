package com.example.inputofcalories.repo.managerflow

import com.example.inputofcalories.domain.managerflow.UpgradeUserToManagerUseCaseImpl
import com.example.inputofcalories.repo.MockDataProvider
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Completable
import org.junit.Test
import java.lang.Exception

class UpgradeUserToManagerTest {
    val upgradeUserRepo = mock<UpgradeUserToManagerRepo>()

    val upgradeUserUseCase = UpgradeUserToManagerUseCaseImpl(upgradeUserRepo)

    @Test
    fun `upgrade succeed test`() {
        val userId = MockDataProvider.user.id

        whenever(upgradeUserRepo.upgrade(userId)).thenReturn( Completable.complete() )

        upgradeUserUseCase.upgrade(userId).test().assertResult()
    }

    @Test
    fun `upgrade failed test`() {
        val userId = MockDataProvider.user.id
        val exception = Exception()

        whenever(upgradeUserRepo.upgrade(userId)).thenReturn( Completable.error(exception) )

        upgradeUserUseCase.upgrade(userId).test().assertError(
            exception
        )
    }
}