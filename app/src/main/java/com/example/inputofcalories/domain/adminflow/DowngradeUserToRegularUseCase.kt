package com.example.inputofcalories.domain.adminflow

import io.reactivex.Completable
import java.util.*

interface DowngradeUserToRegularUseCase {
    fun downgrade(userId: UUID): Completable
}