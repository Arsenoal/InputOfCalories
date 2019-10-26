package com.example.inputofcalories.domain.regularflow

import io.reactivex.Single

interface CheckDailyCaloriesDailyLimitUseCase {
    fun check(): Single<Boolean>
}