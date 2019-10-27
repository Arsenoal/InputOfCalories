package com.example.inputofcalories.repo.regularflow

import com.example.inputofcalories.domain.regularflow.CheckDailyCaloriesDailyLimitUseCaseImpl
import com.example.inputofcalories.repo.MockDataProvider
import com.example.inputofcalories.repo.user.GetUserRepo
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Test

class DailyCaloriesProviderTest {
    val dailyCaloriesProviderRepo = mock<DailyCaloriesProviderRepo>()

    val mealsProviderRepo = mock<MealsProviderRepo>()

    val getUserRepo = mock<GetUserRepo>()

    val checkDailyCaloriesUseCase = CheckDailyCaloriesDailyLimitUseCaseImpl(
        mealsProviderRepo,
        getUserRepo,
        dailyCaloriesProviderRepo)

    @Test
    fun `check limit not exceeded test`() {
        val userId = MockDataProvider.user.id

        whenever(dailyCaloriesProviderRepo.provide(userId)).thenReturn( Single.just("1200") )
        whenever(mealsProviderRepo.getMealsByUserId(userId)).thenReturn( Single.just(
            MockDataProvider.mealsList) )
        whenever(getUserRepo.get()).thenReturn( Single.just(MockDataProvider.user) )

        checkDailyCaloriesUseCase.check().test().assertResult(
            false
        )
    }

    @Test
    fun `check limit exceeded test`() {
        val userId = MockDataProvider.user.id

        whenever(dailyCaloriesProviderRepo.provide(userId)).thenReturn( Single.just("400") )
        whenever(mealsProviderRepo.getMealsByUserId(userId)).thenReturn( Single.just(
            MockDataProvider.mealsList) )
        whenever(getUserRepo.get()).thenReturn( Single.just(MockDataProvider.user) )

        checkDailyCaloriesUseCase.check().test().assertResult(
            true
        )
    }

    @Test
    fun `daily calories repo exception handle test`() {
        val userId = MockDataProvider.user.id
        val exception = Exception()

        whenever(dailyCaloriesProviderRepo.provide(userId)).thenReturn( Single.error(exception) )
        whenever(mealsProviderRepo.getMealsByUserId(userId)).thenReturn( Single.just(
            MockDataProvider.mealsList) )
        whenever(getUserRepo.get()).thenReturn( Single.just(MockDataProvider.user) )

        checkDailyCaloriesUseCase.check().test().assertError(
            exception
        )
    }

    @Test
    fun `meals provider repo exception handle test`() {
        val userId = MockDataProvider.user.id
        val exception = Exception()

        whenever(dailyCaloriesProviderRepo.provide(userId)).thenReturn( Single.just("400") )
        whenever(mealsProviderRepo.getMealsByUserId(userId)).thenReturn( Single.error(exception) )
        whenever(getUserRepo.get()).thenReturn( Single.just(MockDataProvider.user) )

        checkDailyCaloriesUseCase.check().test().assertError(
            exception
        )
    }

    @Test
    fun `get user repo exception handle test`() {
        val userId = MockDataProvider.user.id
        val exception = Exception()

        whenever(dailyCaloriesProviderRepo.provide(userId)).thenReturn( Single.just("400") )
        whenever(mealsProviderRepo.getMealsByUserId(userId)).thenReturn( Single.just(
            MockDataProvider.mealsList) )
        whenever(getUserRepo.get()).thenReturn( Single.error(exception) )

        checkDailyCaloriesUseCase.check().test().assertError(
            exception
        )
    }
}