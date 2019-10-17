package com.example.inputofcalories.domain.adminflow

import io.reactivex.Completable
import java.util.*

interface UpgradeUserToManagerUseCase {
    fun upgrade(userId: UUID): Completable
}