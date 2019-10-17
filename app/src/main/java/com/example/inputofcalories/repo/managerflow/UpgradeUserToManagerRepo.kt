package com.example.inputofcalories.repo.managerflow

import io.reactivex.Completable
import java.util.*

interface UpgradeUserToManagerRepo {
    fun upgrade(userId: UUID): Completable
}