package com.example.inputofcalories.domain.managerflow

import io.reactivex.Completable

interface UpgradeUserToManagerUseCase {
    fun upgrade(userId: String): Completable
}