package com.example.inputofcalories.domain.adminflow

import io.reactivex.Completable

interface DowngradeUserToManagerUseCase {
    fun downgrade(userId: String): Completable
}