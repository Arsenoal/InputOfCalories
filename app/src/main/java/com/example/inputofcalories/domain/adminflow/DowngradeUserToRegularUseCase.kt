package com.example.inputofcalories.domain.adminflow

import io.reactivex.Completable

interface DowngradeUserToRegularUseCase {
    fun downgrade(userId: String): Completable
}