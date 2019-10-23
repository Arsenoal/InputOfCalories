package com.example.inputofcalories.domain.adminflow

import io.reactivex.Completable

interface UpgradeUserToManagerUseCase {
    fun upgrade(userId: String): Completable
}