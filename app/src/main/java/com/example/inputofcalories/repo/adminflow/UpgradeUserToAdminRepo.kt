package com.example.inputofcalories.repo.adminflow

import io.reactivex.Completable
import java.util.*

interface UpgradeUserToAdminRepo {
    fun upgrade(userId: UUID): Completable
}