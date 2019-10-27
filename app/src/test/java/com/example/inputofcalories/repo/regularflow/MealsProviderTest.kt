package com.example.inputofcalories.repo.regularflow

import com.example.inputofcalories.domain.regularflow.GetMealsFilteredUseCaseImpl
import com.example.inputofcalories.domain.regularflow.GetMealsUseCaseImpl
import com.example.inputofcalories.entity.presentation.regular.*
import com.example.inputofcalories.repo.MockDataProvider
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Test

class MealsProviderTest {

    val providerRepo = mock<MealsProviderRepo>()

    val filterUseCase = GetMealsFilteredUseCaseImpl(providerRepo)

    val getMealsUseCase = GetMealsUseCaseImpl(providerRepo)

    @Test
    fun `on filter meals success test`() {
        val uId = "4449fc18-0210-4f09-adfd-ed216369a173"

        val dateParams = MealDateParams(
            year = "2019",
            month = "10",
            dayOfMonth = "27")
        val timeParams = LunchTime()

        val validFilterParams = MealFilterParams(dateParams, timeParams)

        whenever(providerRepo.getMealsByUserId(uId)).thenReturn(
            Single.just(MockDataProvider.mealsList)
        )

        filterUseCase.get(uId, validFilterParams).test().assertResult(MockDataProvider.filteredMealsList)
    }

    @Test
    fun `on get meals success test`() {
        val uId = "4449fc18-0210-4f09-adfd-ed216369a173"

        whenever(providerRepo.getMealsByUserId(uId)).thenReturn(
            Single.just(MockDataProvider.mealsList)
        )

        getMealsUseCase.get(uId).test().assertResult(MockDataProvider.mealsList)
    }

    @Test
    fun `on get meals fail test`() {
        val uId = "4449fc18-0210-4f09-adfd-ed216369a173"

        val exception = Exception()

        whenever(providerRepo.getMealsByUserId(uId)).thenReturn(
            Single.error(exception)
        )

        getMealsUseCase.get(uId).test().assertError(exception)
    }

}