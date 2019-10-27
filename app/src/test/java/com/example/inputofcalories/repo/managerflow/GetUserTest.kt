package com.example.inputofcalories.repo.managerflow

import com.example.inputofcalories.domain.user.GetUserUseCaseImpl
import com.example.inputofcalories.repo.MockDataProvider
import com.example.inputofcalories.repo.user.GetUserRepo
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Test

class GetUserTest {
    val getUserRepo = mock<GetUserRepo>()

    val getUserUseCase = GetUserUseCaseImpl(getUserRepo)

    @Test
    fun `get user succeed test`() {
        whenever(getUserRepo.get()).thenReturn( Single.just(MockDataProvider.user) )

        getUserUseCase.get().test().assertResult(
            MockDataProvider.user
        )
    }

    @Test
    fun `get user fail test`() {
        val exception = Exception()

        whenever(getUserRepo.get()).thenReturn( Single.error(exception) )

        getUserUseCase.get().test().assertError(
            exception
        )
    }
}