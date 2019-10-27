package com.example.inputofcalories.repo.managerflow

import com.example.inputofcalories.domain.managerflow.DowngradeUserToRegularUseCaseImpl
import com.example.inputofcalories.repo.MockDataProvider
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Completable
import org.junit.Test

class DowngradeToRegularTest {
    val downgradeManagerToRegularUserRepo = mock<DowngradeManagerToRegularUserRepo>()

    val downgradeUserToRegularUseCase = DowngradeUserToRegularUseCaseImpl(downgradeManagerToRegularUserRepo)

    @Test
    fun `downgrade user to regular succeed`() {
        whenever(downgradeManagerToRegularUserRepo.downgrade(MockDataProvider.user.id)).thenReturn(
            Completable.complete()
        )

        downgradeUserToRegularUseCase.downgrade(MockDataProvider.user.id).test().assertResult()
    }

    @Test
    fun `downgrade user to regular failed`() {
        val exception = Exception()

        whenever(downgradeManagerToRegularUserRepo.downgrade(MockDataProvider.user.id)).thenReturn(
            Completable.error(exception)
        )

        downgradeUserToRegularUseCase.downgrade(MockDataProvider.user.id).test().assertError(
            exception
        )
    }

}