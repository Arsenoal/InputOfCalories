package com.example.inputofcalories.domain.adminflow

import io.reactivex.Completable
import java.util.*

interface DowngradeUserToManagerUseCase {
    fun downgrade(userId: UUID): Completable
}