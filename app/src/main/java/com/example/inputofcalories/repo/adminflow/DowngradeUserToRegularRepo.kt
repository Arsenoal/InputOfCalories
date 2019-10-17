package com.example.inputofcalories.repo.adminflow

import io.reactivex.Completable
import java.util.*

interface DowngradeUserToRegularRepo {
    fun downgrade(userId: UUID): Completable
}