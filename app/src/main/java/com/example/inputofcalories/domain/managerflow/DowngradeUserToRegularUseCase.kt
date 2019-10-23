package com.example.inputofcalories.domain.managerflow

import io.reactivex.Completable

interface DowngradeUserToRegularUseCase {
    fun downgrade(userId: String): Completable
}