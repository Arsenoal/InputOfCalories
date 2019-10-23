package com.example.inputofcalories.repo.adminflow

import io.reactivex.Completable

interface DowngradeUserToRegularRepo {
    fun downgrade(userId: String): Completable
}