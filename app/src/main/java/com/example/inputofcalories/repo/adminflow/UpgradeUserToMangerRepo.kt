package com.example.inputofcalories.repo.adminflow

import io.reactivex.Completable
import java.util.*

interface UpgradeUserToMangerRepo {
    fun upgrade(userId: UUID): Completable
}