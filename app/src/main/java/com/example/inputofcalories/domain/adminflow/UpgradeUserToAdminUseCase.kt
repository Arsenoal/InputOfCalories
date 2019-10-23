package com.example.inputofcalories.domain.adminflow

import io.reactivex.Completable
import java.util.*

interface UpgradeUserToAdminUseCase {
    fun upgrade(userId: String): Completable
}