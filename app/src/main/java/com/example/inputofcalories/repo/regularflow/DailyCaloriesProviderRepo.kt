package com.example.inputofcalories.repo.regularflow

import io.reactivex.Single

interface DailyCaloriesProviderRepo {
    fun provide(userId: String): Single<String>
}