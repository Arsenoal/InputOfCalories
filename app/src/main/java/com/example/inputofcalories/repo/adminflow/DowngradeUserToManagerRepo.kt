package com.example.inputofcalories.repo.adminflow

import io.reactivex.Completable
import java.util.*

interface DowngradeUserToManagerRepo {
    fun downgrade(userId: UUID): Completable
}