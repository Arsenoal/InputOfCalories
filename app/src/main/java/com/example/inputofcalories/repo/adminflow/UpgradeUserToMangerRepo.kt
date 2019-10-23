package com.example.inputofcalories.repo.adminflow

import io.reactivex.Completable

interface UpgradeUserToMangerRepo {
    fun upgrade(userId: String): Completable
}