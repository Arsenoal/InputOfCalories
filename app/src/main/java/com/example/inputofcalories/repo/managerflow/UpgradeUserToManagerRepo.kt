package com.example.inputofcalories.repo.managerflow

import io.reactivex.Completable

interface UpgradeUserToManagerRepo {
    fun upgrade(userId: String): Completable
}