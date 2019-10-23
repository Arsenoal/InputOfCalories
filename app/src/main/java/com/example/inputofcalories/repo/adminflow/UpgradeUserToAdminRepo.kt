package com.example.inputofcalories.repo.adminflow

import io.reactivex.Completable

interface UpgradeUserToAdminRepo {
    fun upgrade(userId: String): Completable
}