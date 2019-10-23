package com.example.inputofcalories.repo.adminflow

import io.reactivex.Completable

interface DowngradeUserToManagerRepo {
    fun downgrade(userId: String): Completable
}