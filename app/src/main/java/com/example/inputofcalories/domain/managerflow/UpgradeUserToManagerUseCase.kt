package com.example.inputofcalories.domain.managerflow

import io.reactivex.Completable
import java.util.*

interface UpgradeUserToManagerUseCase {
    fun upgrade(userId: UUID): Completable
}