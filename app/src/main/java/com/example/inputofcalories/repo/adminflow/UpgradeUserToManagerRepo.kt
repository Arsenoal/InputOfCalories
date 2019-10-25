package com.example.inputofcalories.repo.adminflow

import io.reactivex.Completable

interface UpgradeUserToManagerRepo {
    fun upgrade(userId: String): Completable
}