package com.example.inputofcalories.repo.adminflow

import com.example.inputofcalories.domain.adminflow.GetAllUsersUseCaseImpl
import com.example.inputofcalories.repo.MockDataProvider
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Test

class GetAllUsersTest {
    val getAllUsersRepo = mock<GetAllUsersRepo>()

    val getAllUsersUseCase = GetAllUsersUseCaseImpl(getAllUsersRepo)

    @Test
    fun `get all users success test`() {
        whenever(getAllUsersRepo.get(MockDataProvider.user.id)).thenReturn(
            Single.just(MockDataProvider.usersList)
        )

        getAllUsersUseCase.get(MockDataProvider.user.id).test().assertResult(
            MockDataProvider.usersList
        )
    }

    @Test
    fun `get all users fail test`() {
        val exception = Exception()

        whenever(getAllUsersRepo.get(MockDataProvider.user.id)).thenReturn(
            Single.error(exception)
        )

        getAllUsersUseCase.get(MockDataProvider.user.id).test().assertError(
            exception
        )
    }
}